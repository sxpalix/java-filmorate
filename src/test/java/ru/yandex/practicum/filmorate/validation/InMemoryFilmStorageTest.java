package ru.yandex.practicum.filmorate.validation;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import java.time.LocalDate;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InMemoryFilmStorageTest {
    private static InMemoryFilmStorage validator = new InMemoryFilmStorage();

    @Test
    public void shouldReturnException() {
        Film film = new Film(0, "QWEDSAsda", "asdas",
                LocalDate.parse("1883-12-28"), 12, 5, new HashSet<>());
        final Exception exception = assertThrows(
                ValidationException.class,
        () -> {
                    validator.postFilm(film);
                }
        );
        assertEquals("Release dare shouldn't be earlier 28.12.1895, or empty values", exception.getMessage());
    }

    @Test
    public void shouldReturnTimeException() {
        Film film = new Film(0, "QWEDSAsda", "asdas",
                LocalDate.parse("1883-12-28"), 12, 5, new HashSet<>());
        final Exception exception = assertThrows(
                ValidationException.class,
                () -> {
                    validator.postFilm(film);
                }
        );
        assertEquals("Release dare shouldn't be earlier 28.12.1895, or empty values", exception.getMessage());
    }

    @Test
    public void shouldReturnIdNotValid() {
        Film film = new Film(0, "QWEDSAsda", "asdas",
                LocalDate.parse("1999-12-28"), 12, 5, new HashSet<>());
        final Exception exception = assertThrows(
                IncorrectValuesException.class,
                () -> {
                    validator.putFilm(film);
                }
        );
        assertEquals("Id not valid", exception.getMessage());
    }
}
