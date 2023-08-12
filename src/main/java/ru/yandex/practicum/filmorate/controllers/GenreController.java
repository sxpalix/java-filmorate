package ru.yandex.practicum.filmorate.controllers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreMpaService;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genres")
public class GenreController {
    private final GenreMpaService<Genre> service;
    @Autowired
    public GenreController(@Qualifier("DbGenreService") GenreMpaService<Genre> service) {
        this.service = service;
    }

    @GetMapping
    public List<Genre> getAll() {
        log.info("GET all genres");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Genre get(@PathVariable int id) throws IncorrectValuesException {
        log.info("GET genre by id");
        return service.get(id);
    }
}
