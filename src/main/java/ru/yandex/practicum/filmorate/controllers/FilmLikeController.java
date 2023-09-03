package ru.yandex.practicum.filmorate.controllers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    FilmLikeService service;

    @PutMapping(path = "/{id}/like/{userId}")
    public void likeTheMovie(@PathVariable int id, @PathVariable int userId) throws IncorrectValuesException {
        log.info("PUT Request. Like the movie");
        service.likeTheMovie(id, userId);
    }

    @DeleteMapping(path = "/{id}/like/{userId}")
    public void unlinkTheMovie(@PathVariable int id, @PathVariable int userId) throws IncorrectValuesException {
        log.info("DELETE Request. Remove like from film");
        service.unlikeTheMovie(id, userId);
    }

    @GetMapping(path = "/popular")
    public List<Film> mostPopularFilm(@RequestParam(defaultValue = "10") int count,
                                      @RequestParam(defaultValue = "0") int genreId,
                                      @RequestParam(defaultValue = "1894") int year) {
        log.info("GET Request. Return list with most popular film");
        return service.mostPopularFilm(count, genreId, year);
    }
}
