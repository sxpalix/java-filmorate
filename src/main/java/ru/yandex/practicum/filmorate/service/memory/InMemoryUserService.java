package ru.yandex.practicum.filmorate.service.memory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.Storage;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service("InMemoryUserService")
public class InMemoryUserService implements UserService {
    private final Storage<User> storage;

    public InMemoryUserService(@Qualifier("UserDbStorage") Storage<User> storage) {
        this.storage = storage;
    }

    private void existenceCheckUser(int id) throws IncorrectValuesException {
        storage.get(id);
    }

    private Set<Integer> returnUsersFriends(int id) throws IncorrectValuesException {
        return storage.get(id).getFriendsList();
    }

    @Override
    public void putToFriends(int id, int friendsId) throws IncorrectValuesException {
        storage.get(friendsId).getFriendsList().add(id);
        storage.get(id).getFriendsList().add(friendsId);
    }

    @Override
    public void unfriending(int id, int friendsId) throws IncorrectValuesException {
        returnUsersFriends(id).remove(friendsId);
        returnUsersFriends(friendsId).remove(id);
    }

    @Override
    public List<User> listOfMutualFriends(int id, int friendsId) throws IncorrectValuesException {
        User user = storage.get(id);
        User otherUser = storage.get(friendsId);

        return user.getFriendsList().stream()
                .filter(t -> otherUser.getFriendsList().contains(t))
                .map(e -> {
                    try {
                        return storage.get(e);
                    } catch (IncorrectValuesException ex) {
                        log.error("User with {} id doesn't exist", id);
                        throw new RuntimeException(ex);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getFriendsList(int id) throws IncorrectValuesException {
        existenceCheckUser(id);
        return returnUsersFriends(id).stream()
                .map(e -> {
                    try {
                        return storage.get(e);
                    } catch (IncorrectValuesException ex) {
                        log.error("User with {} id doesn't exist", id);
                        throw new RuntimeException(ex);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getUsers() {
        return storage.getAll();
    }

    @Override
    public User postUser(User user) throws ValidationException, IncorrectValuesException {
        return storage.post(user);
    }

    @Override
    public User putUser(User user) throws ValidationException, IncorrectValuesException {
        return storage.put(user);
    }

    @Override
    public User getUserById(int id) throws IncorrectValuesException {
        return storage.get(id);
    }
}