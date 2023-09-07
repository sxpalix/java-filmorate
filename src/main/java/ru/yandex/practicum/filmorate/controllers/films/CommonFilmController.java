package ru.yandex.practicum.filmorate.controllers.films;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.filmLike.FilmLikeService;
import java.util.List;

@RestController
@RequestMapping("/films/common")
@Slf4j
@AllArgsConstructor
public class CommonFilmController {
    private final FilmLikeService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Film> commonFilms(@RequestParam(name = "userId") String userId, @RequestParam(name = "friendId") String friendId) {
        log.info("GET common films for userId={} and userId={} with sorting by popular", userId, friendId);
        return service.commonFilms(Integer.parseInt(userId), Integer.parseInt(friendId));
    }
}
