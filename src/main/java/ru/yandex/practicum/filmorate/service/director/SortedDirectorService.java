package ru.yandex.practicum.filmorate.service.director;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SortedDirectorService {
    private final JdbcTemplate template;
    private final FilmRowMapper mapper;

    public List<Film> getDirectorsSortedList(int id, String sortedBy) throws IncorrectValuesException {
        List<Film> films = new ArrayList<>();
        if (sortedBy.equals("year")) {
            String sql = "SELECT * FROM FILM\n" +
                    "INNER JOIN (SELECT * FROM FILM_DIRECTOR WHERE DIRECTOR_ID = ?) AS dir ON FILM.id = dir.film_id\n" +
                    "GROUP BY release_date\n" +
                    "ORDER BY release_date;";
            films = template.query(sql, mapper.getFilmRawMember(), id);
        } else if (sortedBy.equals("likes")) {
            String sql = "SELECT id, name, description, release_date, duration, mpa_id, rating, dir.director_id, COUNT(l.USER_ID) AS likes\n" +
                    "FROM FILM\n" +
                    "INNER JOIN (SELECT * FROM FILM_DIRECTOR WHERE DIRECTOR_ID = ?) AS dir ON FILM.id = dir.film_id\n" +
                    "LEFT JOIN FILM_LIKES AS l ON film.id = l.film_id\n" +
                    "GROUP BY id\n" +
                    "ORDER BY likes DESC;";
            films = template.query(sql, mapper.getFilmRawMember(), id);
        }
        if (films.isEmpty()) {
            log.error("Make sure the request {} is correct or the director {} is available", sortedBy, id);
            throw new IncorrectValuesException("Make sure the request is correct or the director is available");
        } else {
            return films;
        }
    }
}
