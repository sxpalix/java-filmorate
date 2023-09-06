package ru.yandex.practicum.filmorate.controllers.users;
import javax.validation.*;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.event.EventService;
import ru.yandex.practicum.filmorate.service.filmLike.FilmLikeService;
import ru.yandex.practicum.filmorate.service.user.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final FilmLikeService filmLikeService;
    private final EventService eventService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAll() {
        log.info("GET all users");
        return userService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User post(@Valid @RequestBody User user) throws ValidationException, IncorrectValuesException {
        log.info("POST new user");
        return userService.post(user);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User put(@Valid @RequestBody User user) throws ValidationException, IncorrectValuesException {
        log.info("PUT updated user");
        return userService.put(user);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable int id) throws IncorrectValuesException {
        log.info("GET user by ID");
        return userService.getUserById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable int id) throws IncorrectValuesException {
        log.info("DELETE user by ID");
        userService.delete(userService.getUserById(id));
        return "User successfully deleted";
    }

    @GetMapping("/{id}/recommendations")
    @ResponseStatus(HttpStatus.OK)
    public List<Film> getRecommendations(@PathVariable int id) {
        log.info("GET recommendations by ID");
        return filmLikeService.getRecommendations(id);
    }

    @GetMapping("/{id}/feed")
    @ResponseStatus(HttpStatus.OK)
    public List<Event> getFeed(@PathVariable int id) throws IncorrectValuesException, ValidationException {
        log.info("GET feed by ID");
        return eventService.getFeed(id);
    }
}
