package ru.yandex.practicum.filmorate.controllers.other;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.mpaGenre.GenreMpaService;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
@AllArgsConstructor
public class MpaController {
    private final GenreMpaService<Mpa> service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Mpa> getAll() {
        log.info("GET all mpa");
        return service.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mpa get(@PathVariable int id) throws IncorrectValuesException {
        log.info("GET mpa by ids");
        return service.get(id);
    }
}
