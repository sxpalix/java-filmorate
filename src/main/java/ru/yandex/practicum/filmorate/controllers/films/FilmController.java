package ru.yandex.practicum.filmorate.controllers.films;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public List<Film> getFilms() {
        log.info("GET all films");
        return filmService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film postFilm(@Valid @RequestBody Film film) throws ValidationException, IncorrectValuesException {
        log.info("POST new film");
        return filmService.post(film);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Film putFilm(@Valid @RequestBody Film film) throws ValidationException, IncorrectValuesException {
        log.info("PUT updated film");
        return filmService.put(film);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Film getFilm(@PathVariable int id) throws IncorrectValuesException {
        log.info("GET film by ID");
        return filmService.getFilm(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteFilm(@PathVariable int id) throws IncorrectValuesException {
        log.info("DELETE film by ID");
        filmService.delete(filmService.getFilm(id));
        return "Film successfully deleted";
    }
}
