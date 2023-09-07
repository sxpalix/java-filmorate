package ru.yandex.practicum.filmorate.controllers.users;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.userLike.UserLikeService;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserLikeController {
    private final UserLikeService service;

    @PutMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void putToFriends(@PathVariable int id, @PathVariable int friendId) throws IncorrectValuesException {
        log.info("PUT user to friends");
        service.putToFriends(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public String unfriending(@PathVariable int id, @PathVariable int friendId) throws IncorrectValuesException {
        log.info("DELETE user from friends");
        service.unfriending(id, friendId);
        return "User successfully deleted from friends";
    }

    @GetMapping("/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getFriendsList(@PathVariable int id) throws IncorrectValuesException {
        log.info("GET friends list by ID");
        return service.getFriendsList(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> listOfMutualFriends(@PathVariable int id, @PathVariable int otherId) throws IncorrectValuesException {
        log.info("GET mutual friends list");
        return service.listOfMutualFriends(id, otherId);
    }
}
