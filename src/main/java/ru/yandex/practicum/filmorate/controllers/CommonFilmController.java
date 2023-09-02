package ru.yandex.practicum.filmorate.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.filmLike.FilmLikeService;

import java.util.List;

@RestController
@RequestMapping("/films/common")
@Slf4j
@AllArgsConstructor
public class CommonFilmController {
    FilmLikeService service;

    @GetMapping
    public List<Film> commonFilms(@RequestParam(name = "userId") String userId, @RequestParam(name = "friendId") String friendId) {
        log.info(userId + "   " + friendId);
        return service.commonFilms(Integer.parseInt(userId), Integer.parseInt(friendId));
    }
}
