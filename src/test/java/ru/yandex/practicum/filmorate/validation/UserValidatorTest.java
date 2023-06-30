package ru.yandex.practicum.filmorate.validation;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.UserValidator;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserValidatorTest {
    private static UserValidator validator = new UserValidator();

    @Test
    public void shouldReturnEmailException() {
        User user = new User("", "sxpailx", LocalDate.parse("2022-05-05"));
        final Exception exception = assertThrows(
                ValidationException.class,
                () -> {
                    validator.postUser(user);
                }
        );
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @.", exception.getMessage());
    }

    @Test
    public void shouldReturnNotEmailException() {
        User user = new User("notEmail", "sxpailx", LocalDate.parse("2022-05-05"));
        final Exception exception = assertThrows(
                ValidationException.class,
                () -> {
                    validator.postUser(user);
                }
        );
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @.", exception.getMessage());
    }

    @Test
    public void shouldReturnLoginException() {
        User user = new User("notEmail@mail.com", "sxp ailx", LocalDate.parse("2022-05-05"));
        final Exception exception = assertThrows(
                ValidationException.class,
                () -> {
                    validator.postUser(user);
                }
        );
        assertEquals("Логин не может быть пустым и содержать пробелы.", exception.getMessage());
    }

    @Test
    public void shouldReturnName() throws ValidationException {
        User user = new User("notEmail@mail.com", "sxpailx", LocalDate.parse("2022-05-05"));
        validator.postUser(user);
        assertEquals("sxpailx", validator.getUsers().get(0).getName());
    }

    @Test
    public void shouldReturnTimeException() {
        User user = new User("notEmail@mail.com", "sxpailx", LocalDate.parse("2024-05-05"));
        final Exception exception = assertThrows(
                ValidationException.class,
                () -> {
                    validator.postUser(user);
                }
        );
        assertEquals("Дата рождения не может быть в будущем.", exception.getMessage());
    }
}
