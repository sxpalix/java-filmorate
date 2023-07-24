package ru.yandex.practicum.filmorate.controllers;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("GET Request");
        return filmService.getFilms();
    }

    @PostMapping
    public Film postFilm(@Valid @RequestBody Film film) throws ValidationException {
        log.info("POST Request");
        return filmService.postFilm(film);
    }

    @PutMapping
    public Film putFilm(@Valid @RequestBody Film film) throws ValidationException, IncorrectValuesException {
        log.info("PUT Request");
        return filmService.putFilm(film);
    }

   @PutMapping(path = "/{id}/like/{userId}")
   public void likeTheMovie(@PathVariable int id, @PathVariable int userId) throws IncorrectValuesException {
       log.info("PUT Request. Like the movie");
       filmService.likeTheMovie(id, userId);
   }

    @DeleteMapping(path = "/{id}/like/{userId}")
    public void unlinkTheMovie(@PathVariable int id, @PathVariable int userId) throws IncorrectValuesException {
        log.info("DELETE Request. Remove like from film");
        filmService.unlikeTheMovie(id, userId);
    }

    @GetMapping(path = "/popular")
    public List<Film> mostPopularFilm(@RequestParam(required = false) Integer count) {
        log.info("GET Request. Return list with most popular film");
        return filmService.mostPopularFilm(count);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable int id) throws IncorrectValuesException {
        log.info("GET Request. Get film by ID");
        return filmService.getFilm(id);
    }
}
