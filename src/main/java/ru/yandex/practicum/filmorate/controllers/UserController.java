package ru.yandex.practicum.filmorate.controllers;
import javax.validation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("GET request");
        return userService.getUsers();
    }

    @PostMapping
    public User postUser(@Valid @RequestBody User user) {
        log.info("Post Request");
        return userService.postUser(user);
    }

    @PutMapping
    public User putUser(@Valid @RequestBody User user) throws ValidationException, IncorrectValuesException {
        log.info("PUT request");
        return userService.putUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void putToFriends(@PathVariable int id, @PathVariable int friendId) throws IncorrectValuesException {
        log.info("PUT request. Put user to friends");
        userService.putToFriends(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void unfriending(@PathVariable int id, @PathVariable int friendId) throws IncorrectValuesException {
        log.info("Delete request. Remove user from friends");
        userService.unfriending(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriendsList(@PathVariable int id) throws IncorrectValuesException {
        log.info("GET request. Get friends list by ID");
        return userService.getFriendsList(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> listOfMutualFriends(@PathVariable int id, @PathVariable int otherId) throws IncorrectValuesException {
        log.info("GET request. Get mutual friends list");
        return userService.listOfMutualFriends(id, otherId);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) throws IncorrectValuesException {
        log.info("GET request. Get user by ID");
        return userService.getUserById(id);
    }
}
