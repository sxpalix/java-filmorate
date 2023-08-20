package ru.yandex.practicum.filmorate.service.filmLike;

import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmLikeService {
    void likeTheMovie(int filmId, int userId) throws IncorrectValuesException;

    void unlikeTheMovie(int filmId, int userId) throws IncorrectValuesException;

    List<Film> mostPopularFilm(Integer count);
}
