package ru.yandex.practicum.filmorate.validation;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import java.time.LocalDate;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserValidatorTest {
    private static InMemoryUserStorage validator = new InMemoryUserStorage();

    @Test
    public void shouldReturnLoginException() throws ValidationException {
        User user = new User(1, "notEmail@mail.com", "sxpailx", "", LocalDate.parse("2022-05-05"), new HashSet<>());
        validator.post(user);
        assertEquals("sxpailx", validator.getAll().get(0).getName());
    }

    @Test
    public void shouldReturnException() {
        User user = new User(2, "notEmail@mail.com", "sxpailx", "name", LocalDate.parse("2022-05-05"), new HashSet<>());
        final Exception exception = assertThrows(
                IncorrectValuesException.class,
                () -> {
                    validator.put(user);
                }
        );
        assertEquals("Id not found", exception.getMessage());
    }
}
