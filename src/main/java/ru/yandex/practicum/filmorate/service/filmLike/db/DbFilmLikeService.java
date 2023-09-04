package ru.yandex.practicum.filmorate.service.filmLike.db;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.enums.Operation;
import ru.yandex.practicum.filmorate.service.event.db.DbEventService;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.service.filmLike.FilmLikeService;
import ru.yandex.practicum.filmorate.service.user.UserService;

import java.util.List;

@SuppressWarnings("checkstyle:Regexp")
@Slf4j
@Service
@AllArgsConstructor
public class DbFilmLikeService implements FilmLikeService {
    private final FilmService filmService;
    private final JdbcTemplate template;
    private final FilmRowMapper mapper;
    private final UserService userService;
    private final DbEventService dbEventService;

    @Override
    public void likeTheMovie(int filmId, int userId) {
        log.info("User with id {} like the movie with {} id", userId, filmId);
        String sql = "INSERT INTO FILM_LIKES(film_id, user_id) VALUES (?, ?);";
        template.update(sql, filmId, userId);
        dbEventService.add(dbEventService.createEventLike(userId, filmId, Operation.ADD));
    }

    public void unlikeTheMovie(int filmId, int userId) throws IncorrectValuesException {
        log.info("User with id {} dislike the movie with {} id", userId, filmId);
        filmService.get(filmId);
        userService.get(userId);
        String sql = "DELETE FROM FILM_LIKES WHERE film_Id =? AND user_Id =?";
        template.update(sql, filmId, userId);

        dbEventService.add(dbEventService.createEventLike(userId, filmId, Operation.REMOVE));
    }

    @Override
    public List<Film> mostPopularFilm(int count, int genreId, int year) {
        if (genreId == 0 && year == 1894) {
            log.info("return most popular film");
            return getPopular(count);
        } else if (genreId != 0 && year == 1894) {
            log.info("return most popular film by genreId");
            return getPopularByGenreId(count, genreId);
        } else if (genreId == 0) {
            log.info("return most popular film by year");
            return getPopularByYear(count, year);
        } else {
            log.info("return most popular film by genreId and year");
            return getPopularByGenreIdAndYear(count, genreId, year);
        }
    }

    @Override
    public List<Film> getRecommendations(int id) {
        log.info("return film recommendations");
        String sql = "SELECT f.id, f.name, f.description, f.release_date, f.duration, f.mpa_id, f.rating\n" +
                     "FROM film_likes AS fl\n" +
                     "LEFT JOIN film AS f ON fl.film_id = f.id\n" +
                     "WHERE user_id =\n" +
                     "    (SELECT user_id\n" +
                     "    FROM film_likes\n" +
                     "    WHERE film_id IN (\n" +
                     "      SELECT film_id\n" +
                     "      FROM film_likes\n" +
                     "      WHERE user_id = ?)\n" +
                     "      AND user_id != ?\n" +
                     "    GROUP BY user_id\n" +
                     "    ORDER BY COUNT(film_id) DESC\n" +
                     "    LIMIT 1)\n" +
                     "  AND film_id NOT IN (\n" +
                     "    SELECT film_id\n" +
                     "    FROM film_likes\n" +
                     "    WHERE user_id = ?)";
        return template.query(sql, mapper.getFilmRawMember(), id, id, id);
    }

    private List<Film> getPopular(int count) {
        log.info("Get popular");
        String sql = "SELECT f.id, " +
                            "f.name, " +
                            "f.description, " +
                            "f.release_date, " +
                            "f.duration, " +
                            "f.mpa_id, " +
                            "f.rating, " +
                            "COUNT(fl.film_id) likes " +
                     "FROM film AS f " +
                     "LEFT JOIN film_likes AS fl ON f.id = fl.film_id " +
                     "GROUP BY f.id " +
                     "ORDER BY likes DESC " +
                     "LIMIT ?";
        return template.query(sql, mapper.getFilmRawMember(), count);
    }

    private List<Film> getPopularByGenreId(int count, int genreId) {
        log.info("Get popular by genre {}", genreId);
        String sql = "SELECT f.id, " +
                            "f.name, " +
                            "f.description, " +
                            "f.release_date, " +
                            "f.duration, " +
                            "f.mpa_id, " +
                            "f.rating, " +
                            "COUNT(fl.film_id) likes " +
                     "FROM film AS f " +
                     "RIGHT JOIN film_genres AS fg ON f.id = fg.film_id " +
                     "LEFT JOIN film_likes AS fl ON f.id = fl.film_id " +
                     "WHERE fg.genre_id = ?" +
                     "GROUP BY f.id " +
                     "ORDER BY likes DESC " +
                     "LIMIT ?";
        return template.query(sql, mapper.getFilmRawMember(), genreId, count);
    }

    private List<Film> getPopularByYear(int count, int year) {
        log.info("Get popular by year {}", year);
        String sql = "SELECT f.id, " +
                            "f.name, " +
                            "f.description, " +
                            "f.release_date, " +
                            "f.duration, " +
                            "f.mpa_id, " +
                            "f.rating, " +
                            "COUNT(fl.film_id) likes " +
                     "FROM film AS f " +
                     "LEFT JOIN film_likes AS fl ON f.id = fl.film_id " +
                     "WHERE EXTRACT(YEAR FROM f.release_date) = ? " +
                     "GROUP BY f.id " +
                     "ORDER BY likes DESC " +
                     "LIMIT ?";
        return template.query(sql, mapper.getFilmRawMember(), year, count);
    }

    private List<Film> getPopularByGenreIdAndYear(int count, int genreId, int year) {
        log.info("Get popular by genre {} and year", genreId, year);
        String sql = "SELECT f.id, " +
                            "f.name, " +
                            "f.description, " +
                            "f.release_date, " +
                            "f.duration, " +
                            "f.mpa_id, " +
                            "f.rating, " +
                            "COUNT(fl.film_id) likes " +
                     "FROM film AS f " +
                     "RIGHT JOIN film_genres AS fg ON f.id = fg.film_id " +
                     "LEFT JOIN film_likes AS fl ON f.id = fl.film_id " +
                     "WHERE EXTRACT(YEAR FROM f.release_date) = ?" +
                     "AND fg.genre_id = ?" +
                     "GROUP BY f.id " +
                     "ORDER BY likes DESC " +
                     "LIMIT ?";
        return template.query(sql, mapper.getFilmRawMember(), year, genreId, count);
    }

    public List<Film> commonFilms(int userId, int friendId) {
        log.info("Get list with common films with {} and {}.", userId, friendId);
        String sql = "SELECT * FROM FILM\n" +
                "INNER JOIN (SELECT FILM_ID, COUNT(USER_ID) AS C FROM FILM_LIKES WHERE USER_ID = ? OR USER_ID = ?\n" +
                "GROUP BY FILM_ID HAVING C > 1) AS CO ON FILM.ID = CO.FILM_ID\n" +
                "INNER JOIN (SELECT FILM_ID, COUNT(USER_ID) AS MAX_COUNT FROM FILM_LIKES GROUP BY FILM_ID)\n" +
                "    AS FL ON FILM.ID=FL.FILM_ID\n" +
                "GROUP BY ID\n" +
                "ORDER BY FL.MAX_COUNT DESC;";
        return template.query(sql, mapper.getFilmRawMember(), userId, friendId);
    }
}
