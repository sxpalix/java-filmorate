package ru.yandex.practicum.filmorate.service.filmSearch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.db.FilmSearchDbStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DbFilmSearchService implements FilmSearchService {
	private final FilmSearchDbStorage storage;

	@Override
	public List<Film> searchFilm(String query, List<String> by) throws IncorrectValuesException {
		log.info("Search films. query = {}; by = {}", query, by);
		return storage.searchFilm(query, by);
	}
}
