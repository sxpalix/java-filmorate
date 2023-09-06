package ru.yandex.practicum.filmorate.service.user.db;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.Storage;
import java.util.List;

@Service("DbUserService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DbUserService implements UserService {
    private final Storage<User> storage;

    private void validations(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    @Override
    public List<User> getAll() {
        return storage.getAll();
    }

    @Override
    public User post(User user) throws ValidationException, IncorrectValuesException {
        validations(user);
        return storage.post(user);
    }

    @Override
    public User put(User user) throws ValidationException, IncorrectValuesException {
        validations(user);
        return storage.put(user);
    }

    @Override
    public User getUserById(int id) throws IncorrectValuesException {
        return storage.get(id);
    }

    @Override
    public void delete(User user) throws IncorrectValuesException {
        storage.delete(user);
    }
}
