package ru.yandex.practicum.filmorate.validators;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class UserValidator {
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 1;

    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    public User postUser(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
            user.setId(id++);
            users.put(user.getId(), user);
            log.info("User added successfully");
        } else {
            user.setId(id++);
            users.put(user.getId(), user);
            log.info("User added successfully");
        }
        return user;
    }

    public User putUser(User user) throws ValidationException {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("Successful Update");
        } else {
            log.error("Id not valid");
            throw new ValidationException("Id not valid");
        }
        return user;
    }
}
