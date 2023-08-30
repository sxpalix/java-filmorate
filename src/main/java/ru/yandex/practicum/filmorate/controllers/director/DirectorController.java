package ru.yandex.practicum.filmorate.controllers.director;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.service.director.DirectorService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/directors")
@AllArgsConstructor
public class DirectorController {
    private final DirectorService service;

    @GetMapping
    public List<Director> getAll() {
        log.info("GET all genres");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Director get(@PathVariable int id) throws IncorrectValuesException {
        log.info("GET genre by id");
        return service.get(id);
    }

    @PostMapping
    public Director post(@RequestBody Director director) throws ValidationException, IncorrectValuesException {
        return service.post(director);
    }

    @PutMapping
    public Director put(@RequestBody Director director) throws ValidationException, IncorrectValuesException {
        return service.put(director);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) throws ValidationException, IncorrectValuesException {
        service.delete(id);
    }
}
