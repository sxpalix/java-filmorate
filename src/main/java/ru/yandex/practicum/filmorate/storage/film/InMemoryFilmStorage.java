package ru.yandex.practicum.filmorate.storage.film;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage{
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 1;

    public void validations(Film film) throws ValidationException {
        if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))) {
            log.error("Release dare shouldn't be earlier 28.12.1895, or empty values");
            throw new ValidationException("Release dare shouldn't be earlier 28.12.1895, or empty values");
        }
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film postFilm(Film film) throws ValidationException {
        validations(film);
        film.setId(id++);
        films.put(film.getId(), film);
        log.info("Task added successfully");
        return film;
    }

    @Override
    public Film putFilm(Film film) throws IncorrectValuesException, ValidationException {
        validations(film);
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
    public Film getFilm(int id) throws IncorrectValuesException {
        if (!films.containsKey(id)) {
            throw new IncorrectValuesException("Incorrect film id");
        }
        return films.get(id);
    }
}