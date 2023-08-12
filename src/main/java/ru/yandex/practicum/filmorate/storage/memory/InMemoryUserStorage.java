package ru.yandex.practicum.filmorate.storage.memory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.storage.validation.UserValid;
import ru.yandex.practicum.filmorate.storage.validation.Valid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("InMemoryUserStorage")
public class InMemoryUserStorage implements Storage<User> {
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 1;

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User post(User user) {
        user.setId(id++);
        users.put(user.getId(), user);
        log.info("User added successfully");
        return user;
    }

    @Override
    public User put(User user) throws IncorrectValuesException {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("Successful Update");
        } else {
            log.error("Id not valid");
            throw new IncorrectValuesException("Id not found");
        }
        return user;
    }

    @Override
    public User get(int id) throws IncorrectValuesException {
        if (!users.containsKey(id) || id < 0) {
            throw new IncorrectValuesException("User not found");
        }
        return users.get(id);
    }
}
