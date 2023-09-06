package ru.yandex.practicum.filmorate.service.user;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.List;

public interface UserService {
    List<User> getAll();

    User post(User user) throws ValidationException, IncorrectValuesException;

    User put(User user) throws ValidationException, IncorrectValuesException;

    User getUserById(int id) throws IncorrectValuesException;

    void delete(User user) throws IncorrectValuesException;
}
