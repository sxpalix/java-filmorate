package ru.yandex.practicum.filmorate.controllers.director;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.director.SortedDirectorService;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films/director")
@AllArgsConstructor
public class SortedDirectorController {
    private SortedDirectorService service;

    @GetMapping("/{directorId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Film> getDirectorsSortedList(@PathVariable int directorId, @RequestParam String sortBy) throws IncorrectValuesException {
        log.info("GET Sort directors film by {}", sortBy);
        return service.getDirectorsSortedList(directorId, sortBy);
    }
}
