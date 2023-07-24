package ru.yandex.practicum.filmorate.service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InMemoryUserService implements UserService {
    private final UserStorage storage;

    public InMemoryUserService(UserStorage storage) {
        this.storage = storage;
    }

    private void existenceCheckUser(int id) throws IncorrectValuesException {
        storage.getUserById(id);
    }

    private Set<Integer> returnUsersFriends(int id) throws IncorrectValuesException {
        return storage.getUserById(id).getFriendsList();
    }

    @Override
    public void putToFriends(int id, int friendsId) throws IncorrectValuesException {
        storage.getUserById(friendsId).getFriendsList().add(id);
        storage.getUserById(id).getFriendsList().add(friendsId);
    }

    @Override
    public void unfriending(int id, int friendsId) throws IncorrectValuesException {
        returnUsersFriends(id).remove(friendsId);
        returnUsersFriends(friendsId).remove(id);
    }

    @Override
    public List<User> listOfMutualFriends(int id, int friendsId) throws IncorrectValuesException {
        User user = storage.getUserById(id);
        User otherUser = storage.getUserById(friendsId);

        return user.getFriendsList().stream()
                .filter(t -> otherUser.getFriendsList().contains(t))
                .map(e -> {
                    try {
                        return storage.getUserById(e);
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
                        return storage.getUserById(e);
                    } catch (IncorrectValuesException ex) {
                        log.error("User with {} id doesn't exist", id);
                        throw new RuntimeException(ex);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getUsers() {
        return storage.getUsers();
    }

    @Override
    public User postUser(User user) {
        return storage.postUser(user);
    }

    @Override
    public User putUser(User user) throws ValidationException, IncorrectValuesException {
        return storage.putUser(user);
    }

    @Override
    public User getUserById(int id) throws IncorrectValuesException {
        return storage.getUserById(id);
    }
}