package ru.yandex.practicum.filmorate.controllers;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validators.FilmValidator;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    FilmValidator films = new FilmValidator();

    @GetMapping
    public List<Film> getFilms() {
        return films.getFilms();
    }

    @PostMapping
    public Film postFilm(@Valid @RequestBody Film film) throws ValidationException {
            return films.postFilm(film);
    }

    @PutMapping
    public Film putFilm(@Valid @RequestBody Film film) throws ValidationException {
        return films.putFilm(film);
    }
}
