package ru.yandex.practicum.filmorate.service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    private UserStorage storage;

    public UserService(UserStorage storage) {
        this.storage = storage;
    }

    private void existenceCheckUser(int id) throws IncorrectValuesException {
        storage.getUserById(id);
    }

    private Set<Integer> returnUsersFriends(int id) throws IncorrectValuesException {
        return storage.getUserById(id).getFriendsList();
    }

    public void putToFriends(int id, int friendsId) throws IncorrectValuesException {
        storage.getUserById(friendsId).getFriendsList().add(id);
        storage.getUserById(id).getFriendsList().add(friendsId);
    }

    public void unfriending(int id, int friendsId) throws IncorrectValuesException {
         returnUsersFriends(id).remove(friendsId);
         returnUsersFriends(friendsId).remove(id);
    }

    public List<User> listOfMutualFriends(int id, int friendsId) throws IncorrectValuesException {
        User user = storage.getUserById(id);
        User otherUser = storage.getUserById(friendsId);

        return user.getFriendsList().stream()
                .filter(t -> otherUser.getFriendsList().contains(t))
                .map(e -> {
                    try {
                        return storage.getUserById(e);
                    } catch (IncorrectValuesException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(Collectors.toList());
    }

    public List<User> getFriendsList(int id) throws IncorrectValuesException {
        existenceCheckUser(id);
        return returnUsersFriends(id).stream()
                .map(e -> {
                    try {
                        return storage.getUserById(e);
                    } catch (IncorrectValuesException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(Collectors.toList());
    }
}
