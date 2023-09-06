package ru.yandex.practicum.filmorate.controllers.films;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.filmSearch.FilmSearchService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmSearchController {
	private final FilmSearchService service;

	@GetMapping(path = "/search")
	@ResponseStatus(HttpStatus.OK)
	public List<Film> searchFilm(@RequestParam String query, @RequestParam List<String> by) throws IncorrectValuesException {
		log.info("GET search films");
		return service.searchFilm(query, by);
	}
}
