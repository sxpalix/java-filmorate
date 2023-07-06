package ru.yandex.practicum.filmorate.controllers;
import javax.validation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.UserValidator;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserValidator users = new UserValidator();

    @GetMapping
    public List<User> getUsers() {
        log.info("GET request");
        return users.getUsers();
    }

    @PostMapping
    public User postUser(@Valid @RequestBody User user) throws ValidationException {
        log.info("Post Request");
        return users.postUser(user);
    }

    @PutMapping
    public User putUser(@Valid @RequestBody User user) throws ValidationException {
        log.info("PUT request");
        return users.putUser(user);
    }
}
