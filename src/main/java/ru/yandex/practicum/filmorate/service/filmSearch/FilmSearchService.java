package ru.yandex.practicum.filmorate.service.filmSearch;

import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmSearchService {

	List<Film> searchFilm(String query, List<String> by) throws IncorrectValuesException;

}
