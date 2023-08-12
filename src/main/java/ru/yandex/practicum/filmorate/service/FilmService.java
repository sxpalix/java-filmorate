package ru.yandex.practicum.filmorate.service;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.*;

public interface FilmService {

    void likeTheMovie(int filmId, int userId) throws IncorrectValuesException;

    void unlikeTheMovie(int filmId, int userId) throws IncorrectValuesException;

    List<Film> mostPopularFilm(Integer count);

    Film putFilm(Film film) throws ValidationException, IncorrectValuesException;

    List<Film> getFilms();

    Film postFilm(Film film) throws ValidationException, IncorrectValuesException;

    Film getFilm(int id) throws IncorrectValuesException;
}
