package ru.yandex.practicum.filmorate.validation;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validators.FilmValidator;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmValidatorTest {
    private static FilmValidator validator = new FilmValidator();

    @Test
    public void shouldReturnNameException() {
         Film film = new Film("", "dasd", LocalDate.parse("1999-12-28"), 12);
        final Exception exception = assertThrows(
                ValidationException.class,
        () -> {
                    validator.postFilm(film);
                }
        );
        assertEquals("Название не может быть пустым.", exception.getMessage());
    }

    @Test
    public void shouldReturnDescriptionSizeException() {
        String description = "";
        for (int i = 0; i < 20; i++) {
            description = description + "tenCharTen";
        }
        Film film = new Film("QWEDSAsda", description + "tt",
                LocalDate.parse("1999-12-28"), 12);
        final Exception exception = assertThrows(
                ValidationException.class,
                () -> {
                    validator.postFilm(film);
                }
        );
        assertEquals("Максимальная длина описания — 200 символов.", exception.getMessage());
    }

    @Test
    public void shouldReturnDescriptionSize() throws ValidationException {
        String description = "";
        for (int i = 0; i < 20; i++) {
            description = description + "tenCharTen";
        }
        Film film = new Film("QWEDSAsda", description,
                LocalDate.parse("1999-12-28"), 12);
        validator.postFilm(film);
        assertEquals(200, film.getDescription().length());
    }

    @Test
    public void shouldReturnDataException() {
        Film film = new Film("QWEDSAsda", "description",
                LocalDate.parse("1895-12-27"), 12);
        final Exception exception = assertThrows(
                ValidationException.class,
                () -> {
                    validator.postFilm(film);
                }
        );
        assertEquals("Дата релиза — не раньше 28 декабря 1895 года.", exception.getMessage());
    }

    @Test
    public void shouldReturnDurationException() {
        Film film = new Film("QWEDSAsda", "description",
                LocalDate.parse("1895-12-29"), 0);
        final Exception exception = assertThrows(
                ValidationException.class,
                () -> {
                    validator.postFilm(film);
                }
        );
        assertEquals("Продолжительность фильма должна быть положительной.", exception.getMessage());
    }
}
