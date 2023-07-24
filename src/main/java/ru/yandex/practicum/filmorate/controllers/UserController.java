package ru.yandex.practicum.filmorate.controllers;
import javax.validation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserStorage userStorage;
    private final UserService userService;

    @Autowired
    public UserController(UserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("GET request");
        return userStorage.getUsers();
    }

    @PostMapping
    public User postUser(@Valid @RequestBody User user) {
        log.info("Post Request");
        return userStorage.postUser(user);
    }

    @PutMapping
    public User putUser(@Valid @RequestBody User user) throws ValidationException, IncorrectValuesException {
        log.info("PUT request");
        return userStorage.putUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void putToFriends(@PathVariable int id, @PathVariable int friendId) throws IncorrectValuesException {
        userService.putToFriends(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void unfriending(@PathVariable int id, @PathVariable int friendId) throws IncorrectValuesException {
        userService.unfriending(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriendsList(@PathVariable int id) throws IncorrectValuesException {
        return userService.getFriendsList(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> listOfMutualFriends(@PathVariable int id, @PathVariable int otherId) throws IncorrectValuesException {
        return userService.listOfMutualFriends(id, otherId);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) throws IncorrectValuesException {
        return userStorage.getUserById(id);
    }
}
