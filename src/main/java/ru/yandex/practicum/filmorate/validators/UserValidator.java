package ru.yandex.practicum.filmorate.validators;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserValidator {
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 1;

    public List<User> getUsers(){
        return new ArrayList<>(users.values());
    }

    public User postUser(User user) throws ValidationException {
        if (user.getEmail().equals("null") || !user.getEmail().contains("@")) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @.");
        }
        if (user.getLogin().equals("null") || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы.");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
        if (user.getName() == null) {
            user.setName(user.getLogin());
            user.setId(id++);
            id++;
            users.put(user.getId(), user);
        } else {
            user.setId(id++);
            users.put(user.getId(), user);
        }
        return user;
    }

    public User putUser(User user) throws ValidationException {
        if (user.getEmail().equals("null") || !user.getEmail().contains("@")) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @.");
        }
        if (user.getLogin().equals("null") || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы.");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
        if (user.getName().equals("null")) {
            user.setName(user.getLogin());
        }
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            throw new ValidationException("Обновление пользователя с несуществующим id.");
        }
        return user;
    }
}
