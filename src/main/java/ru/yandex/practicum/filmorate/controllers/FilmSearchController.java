package ru.yandex.practicum.filmorate.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.filmSearch.FilmSearchService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmSearchController {
	FilmSearchService service;

	@GetMapping(path = "/search")
	public List<Film> searchFilm(@RequestParam String query, @RequestParam List<String> by) throws IncorrectValuesException {
		log.info("GET Request. Search films");
		return service.searchFilm(query, by);
	}
}
