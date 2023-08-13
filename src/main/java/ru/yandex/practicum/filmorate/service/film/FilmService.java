package ru.yandex.practicum.filmorate.service.film;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.*;

public interface FilmService {
    Film put(Film film) throws ValidationException, IncorrectValuesException;

    List<Film> getAll();

    Film post(Film film) throws ValidationException, IncorrectValuesException;

    Film get(int id) throws IncorrectValuesException;
}
