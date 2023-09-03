package ru.yandex.practicum.filmorate.controllers.films;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController {
    private final  FilmService filmService;

    @GetMapping
    public List<Film> getFilms() {
        log.info("GET Request");
        return filmService.getAll();
    }

    @PostMapping
    public Film postFilm(@Valid @RequestBody Film film) throws ValidationException, IncorrectValuesException {
        log.info("POST Request");
        return filmService.post(film);
    }

    @PutMapping
    public Film putFilm(@Valid @RequestBody Film film) throws ValidationException, IncorrectValuesException {
        log.info("PUT Request");
        return filmService.put(film);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable int id) throws IncorrectValuesException {
        log.info("GET Request. Get film by ID");
        return filmService.get(id);
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable int id) throws IncorrectValuesException {
        log.info("DELETE Request. Delete film by ID");
        filmService.delete(filmService.get(id));
    }
}
