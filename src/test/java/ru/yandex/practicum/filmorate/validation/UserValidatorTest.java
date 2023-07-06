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
    public void shouldReturnLoginException() throws ValidationException {
        User user = new User(1, "notEmail@mail.com", "sxpailx", "", LocalDate.parse("2022-05-05"));
        validator.putUser(user);
        assertEquals("Id not valid", validator.getUsers().get(0).getName());
    }

    @Test
    public void shouldReturnException() {
        User user = new User(1, "notEmail@mail.com", "sxpailx", "name", LocalDate.parse("2022-05-05"));
        final Exception exception = assertThrows(
                ValidationException.class,
                () -> {
                    validator.putUser(user);
                }
        );
        assertEquals("Id not valid", exception.getMessage());
    }
}
