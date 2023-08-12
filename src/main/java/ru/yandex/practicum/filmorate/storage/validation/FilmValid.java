package ru.yandex.practicum.filmorate.storage.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@Slf4j
@Component("FilmValid")
public class FilmValid implements Valid<Film> {
    @Override
    public void validations(Film film) throws ValidationException {
        if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))) {
            log.error("Release dare shouldn't be earlier 28.12.1895, or empty values");
            throw new ValidationException("Release dare shouldn't be earlier 28.12.1895, or empty values");
        }
    }
}
