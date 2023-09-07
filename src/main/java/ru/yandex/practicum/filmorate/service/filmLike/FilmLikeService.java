package ru.yandex.practicum.filmorate.service.filmLike;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.List;

public interface FilmLikeService {
    Film likeFilm(int filmId, int userId) throws IncorrectValuesException;

    Film unlikeFilm(int filmId, int userId) throws IncorrectValuesException;

    List<Film> mostPopularFilm(int count, int genreId, int year);

    List<Film> getRecommendations(int id);

    List<Film> commonFilms(int userId, int friendId);
}
