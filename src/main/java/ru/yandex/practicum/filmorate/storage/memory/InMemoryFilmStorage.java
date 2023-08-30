package ru.yandex.practicum.filmorate.storage.memory;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.*;

@Slf4j
public class InMemoryFilmStorage implements Storage<Film> {
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 1;

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film post(Film film) throws ValidationException {
        film.setId(id++);
        films.put(film.getId(), film);
        log.info("Task added successfully");
        return film;
    }

    @Override
    public Film put(Film film) throws IncorrectValuesException, ValidationException {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Update was successful");
        } else {
            log.error("Id not valid");
            throw new IncorrectValuesException("Id not valid");
        }
        return film;
    }

    @Override
    public Film get(int id) throws IncorrectValuesException {
        if (!films.containsKey(id)) {
            throw new IncorrectValuesException("Incorrect film id");
        }
        return films.get(id);
    }

    @Override
    public void delete(Film film) throws IncorrectValuesException {
        if (!films.containsKey(id)) {
            throw new IncorrectValuesException("Incorrect film id");
        }
        films.remove(film.getId());
    }
}