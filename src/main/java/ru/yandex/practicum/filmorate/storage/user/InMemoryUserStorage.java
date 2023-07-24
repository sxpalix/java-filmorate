package ru.yandex.practicum.filmorate.storage.user;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage{
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 1;

    private void validations(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }
    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User postUser(User user) {
        validations(user);
        user.setId(id++);
        users.put(user.getId(), user);
        log.info("User added successfully");
        return user;
    }

    @Override
    public User putUser(User user) throws IncorrectValuesException {
        validations(user);
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
    public User getUserById(int id) throws IncorrectValuesException {
        if (!users.containsKey(id) || id < 0) {
            throw new IncorrectValuesException("User not found");
        }
        return users.get(id);
    }
}
