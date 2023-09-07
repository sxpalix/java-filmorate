package ru.yandex.practicum.filmorate.controllers.films;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.filmLike.FilmLikeService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmLikeController {
    private final FilmLikeService service;

    @PutMapping(path = "/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Film likeFilm(@PathVariable int id, @PathVariable int userId) throws IncorrectValuesException {
        log.info("PUT like for film");
        return service.likeFilm(id, userId);
    }

    @DeleteMapping(path = "/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Film unlikeFilm(@PathVariable int id, @PathVariable int userId) throws IncorrectValuesException {
        log.info("DELETE like from film");
        return service.unlikeFilm(id, userId);
    }

    @GetMapping(path = "/popular")
    @ResponseStatus(HttpStatus.OK)
    public List<Film> mostPopularFilm(@RequestParam(defaultValue = "10") int count,
                                      @RequestParam(defaultValue = "0") int genreId,
                                      @RequestParam(defaultValue = "1894") int year) {
        log.info("GET list with most popular film");
        return service.mostPopularFilm(count, genreId, year);
    }
}
