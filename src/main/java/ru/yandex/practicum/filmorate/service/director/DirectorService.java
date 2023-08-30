package ru.yandex.practicum.filmorate.service.director;

import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;

public interface DirectorService {
    Director put(Director director) throws ValidationException, IncorrectValuesException;

    List<Director> getAll();

    Director post(Director director) throws ValidationException, IncorrectValuesException;

    Director get(int id) throws IncorrectValuesException;

    void delete(int id);
}
