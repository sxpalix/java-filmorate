package ru.yandex.practicum.filmorate.controllers.other;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public List<Mpa> getAll() {
        log.info("GET all mpa");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Mpa get(@PathVariable int id) throws IncorrectValuesException {
        log.info("GET mpa by ids");
        return service.get(id);
    }
}
