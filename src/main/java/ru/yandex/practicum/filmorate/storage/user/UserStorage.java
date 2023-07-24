package ru.yandex.practicum.filmorate.storage.user;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.List;

public interface UserStorage {
    List<User> getUsers();
    User postUser(User user);
    User putUser(User user) throws ValidationException, IncorrectValuesException;
    User getUserById(int id) throws IncorrectValuesException;
}
