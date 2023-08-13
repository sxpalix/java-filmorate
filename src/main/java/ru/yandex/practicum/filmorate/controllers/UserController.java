package ru.yandex.practicum.filmorate.controllers;
import javax.validation.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        log.info("GET request");
        return userService.getAll();
    }

    @PostMapping
    public User postUser(@Valid @RequestBody User user) throws ValidationException, IncorrectValuesException {
        log.info("Post Request");
        return userService.post(user);
    }

    @PutMapping
    public User putUser(@Valid @RequestBody User user) throws ValidationException, IncorrectValuesException {
        log.info("PUT request");
        return userService.put(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) throws IncorrectValuesException {
        log.info("GET request. Get user by ID");
        return userService.get(id);
    }
}
