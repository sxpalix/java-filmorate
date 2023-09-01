package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component("FilmSearchDbStorage")
public class FilmSearchDbStorage {

	private final JdbcTemplate template;
	private final FilmRowMapper mapper;

	@Autowired
	public FilmSearchDbStorage(JdbcTemplate template, FilmRowMapper mapper) {
		this.template = template;
		this.mapper = mapper;
	}

	public List<Film> searchFilm(String query, List<String> by) throws IncorrectValuesException {
		log.info("Search films. query = {}; by = {}", query, by);

		Map<String, String> convertationMap = Map.of(
				"title", "film.description",
				"director", "dir.name");

		List<String> convertedBy = by.stream()
				.map(convertationMap::get)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());

		if (convertedBy.size() == 0) {
			log.warn("incorrect search terms");
			throw new IncorrectValuesException("incorrect search terms");
		}

		String searchConditions = convertedBy.stream()
				.map(s -> s + " LIKE '%" + query.toLowerCase() + "%'")
				.collect(Collectors.joining(" OR "));

		String sql = "SELECT film.id, film.name, film.description, film.release_date, film.duration, film.mpa_id, film.rating\n" +
				"                FROM FILM\n" +
				"                LEFT OUTER JOIN FILM_DIRECTOR AS fd ON Film.id = fd.FILM_ID\n" +
				"                LEFT OUTER JOIN  DIRECTOR AS dir ON fd.DIRECTOR_ID=dir.ID\n" +
				"                WHERE " + searchConditions +
				"                ORDER BY film.id DESC";

		return template.query(sql, mapper.getFilmRawMember());
	}

}
