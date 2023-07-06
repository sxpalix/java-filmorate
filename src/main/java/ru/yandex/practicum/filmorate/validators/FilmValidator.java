package ru.yandex.practicum.filmorate.validators;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class FilmValidator {
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 1;

    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    public Film postFilm(Film film) throws ValidationException {
        if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))) {
            log.error("Release dare shouldn't be earlier 28.12.1895, or empty values");
            throw new ValidationException("Release dare shouldn't be earlier 28.12.1895, or empty values");
        }
        film.setId(id++);
        films.put(film.getId(), film);
        log.info("Task added successfully");
        return film;
    }

    public Film putFilm(Film film) throws ValidationException {
        if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))) {
            log.error("Release dare shouldn't be earlier 28.12.1895, or empty values");
            throw new ValidationException("Release dare shouldn't be earlier 28.12.1895, or empty values");
        }
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Update was successful");
        } else {
            log.error("Id not valid");
            throw new ValidationException("Id not valid");
        }
        return film;
    }
}