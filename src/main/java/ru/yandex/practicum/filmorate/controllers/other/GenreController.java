package ru.yandex.practicum.filmorate.controllers.other;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.mpaGenre.GenreMpaService;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genres")
@AllArgsConstructor
public class GenreController {
    private final GenreMpaService<Genre> service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Genre> getAll() {
        log.info("GET all genres");
        return service.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Genre get(@PathVariable int id) throws IncorrectValuesException {
        log.info("GET genre by id");
        return service.get(id);
    }
}
