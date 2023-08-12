package ru.yandex.practicum.filmorate.service.dbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.Storage;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service("DbFilmService")
public class DbFilmService implements FilmService {
    Storage<Film> storage;
    JdbcTemplate template;
    FilmRowMapper mapper;
    UserService service;

    @Autowired
    public DbFilmService(@Qualifier("FilmDbStorage")Storage<Film> storage, JdbcTemplate template,
                         FilmRowMapper mapper, @Qualifier("DbUserService") UserService service) {
        this.template = template;
        this.storage = storage;
        this.mapper = mapper;
        this.service = service;
    }

    @Override
    public void likeTheMovie(int filmId, int userId) {
        String sql = "INSERT INTO FILM_LIKES(film_id, user_id) VALUES (?, ?);";
        template.update(sql, filmId, userId);
    }

    @Override
    public void unlikeTheMovie(int filmId, int userId) throws IncorrectValuesException {
        getFilm(filmId);
        service.getUserById(userId);
        String sql = "DELETE FROM FILM_LIKES WHERE film_Id =? AND user_Id =?";
        template.update(sql, filmId, userId);
    }

    @Override
    public List<Film> mostPopularFilm(Integer count) {
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

    @Override
    public Film putFilm(Film film) throws ValidationException, IncorrectValuesException {
        return storage.put(film);
    }

    @Override
    public List<Film> getFilms() {
        return storage.getAll();
    }

    @Override
    public Film postFilm(Film film) throws ValidationException, IncorrectValuesException {
        return storage.post(film);
    }

    @Override
    public Film getFilm(int id) throws IncorrectValuesException {
        return storage.get(id);
    }
}
