package ru.yandex.practicum.filmorate.service.filmLike.db;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.service.filmLike.FilmLikeService;
import ru.yandex.practicum.filmorate.service.user.UserService;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class DbFilmLikeService implements FilmLikeService {
    private final FilmService filmService;
    private final JdbcTemplate template;
    private final FilmRowMapper mapper;
    private final UserService userService;

    @Override
    public void likeTheMovie(int filmId, int userId) {
        log.info("User with id {} like the movie with {} id", userId, filmId);
        String sql = "INSERT INTO FILM_LIKES(film_id, user_id) VALUES (?, ?);";
        template.update(sql, filmId, userId);
    }

    public void unlikeTheMovie(int filmId, int userId) throws IncorrectValuesException {
        log.info("User with id {} dislike the movie with {} id", userId, filmId);
        filmService.get(filmId);
        userService.get(userId);
        String sql = "DELETE FROM FILM_LIKES WHERE film_Id =? AND user_Id =?";
        template.update(sql, filmId, userId);
    }

    public List<Film> mostPopularFilm(Integer count) {
        log.info("return most popular film");
        int limit = Optional.ofNullable(count).orElse(10);
        String sql = "SELECT id, name, description, release_date, duration, mpa_id, rating FROM (\n" +
                "SELECT id, name, description, release_date, duration, mpa_id, rating," +
                " COUNT(user_id) AS likes FROM FILM\n" +
                "LEFT OUTER JOIN Film_Likes AS fl ON Film.id = fl.FILM_ID\n" +
                "GROUP BY id\n" +
                "ORDER BY likes DESC\n" +
                "Limit ?);";
        return template.query(sql, mapper.getFilmRawMember(), limit);
    }
}
