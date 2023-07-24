package ru.yandex.practicum.filmorate.storage.film;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.List;

public interface FilmStorage {
    Film putFilm(Film film) throws ValidationException, IncorrectValuesException;
    List<Film> getFilms();
    Film postFilm(Film film) throws ValidationException;
    Film getFilm(int id) throws IncorrectValuesException;
}
