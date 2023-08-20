package ru.yandex.practicum.filmorate.storage.db;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.storage.validation.Valid;
import java.util.List;

@Slf4j
@Component("FilmDbStorage")
public class FilmDbStorage implements Storage<Film> {
    private final JdbcTemplate template;
    private final Valid<Film> valid;
    private final FilmRowMapper mapper;

    @Autowired
    public FilmDbStorage(JdbcTemplate template, Valid<Film> valid, FilmRowMapper mapper) {
        this.template = template;
        this.valid = valid;
        this.mapper = mapper;
    }

    @Override
    public Film put(Film film) throws ValidationException, IncorrectValuesException {
        log.info("put film from date base");
        valid.validations(film);
        String sql = "UPDATE FILM SET NAME=?, DESCRIPTION=?, release_date=?, duration=?, mpa_id=?, rating=?" +
                "  WHERE ID=?";
        template.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId(), film.getRating(), film.getId());
        template.update("DELETE FROM FILM_GENRES WHERE FILM_ID=?", film.getId());
        String saveFilmGenre = "INSERT INTO FILM_GENRES(FILM_ID, GENRE_ID) VALUES (?, ?)";
        int id = film.getId();
        for (Genre genre: film.getGenres()) {
            template.update(saveFilmGenre, id, genre.getId());
        }
        return get(film.getId());
    }

    @Override
    public List<Film> getAll() {
        log.info("get all films from date base");
        String sql = "SELECT * FROM FILM";
        return template.query(sql, mapper.getFilmRawMember());
    }

    @Override
    public Film post(Film film) throws ValidationException {
        log.info("post film into date base");
        valid.validations(film);
        String sql = "INSERT INTO FILM(id, name, description, release_date, duration, mpa_id, rating)" +
                " VALUES (DEFAULT, ?, ?, ?, ?, ?, ?);";
        template.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId(), film.getRating());
        String saveFilmGenre = "INSERT INTO FILM_GENRES(FILM_ID, GENRE_ID) VALUES (?, ?)";
        int id = getByName(film.getName()).getId();
        for (Genre genre: film.getGenres()) {
            template.update(saveFilmGenre, id, genre.getId());
        }
        return getByName(film.getName());
    }

    @Override
    public Film get(int id) throws IncorrectValuesException {
        log.info("get film from date base");
        String sql = "SELECT * FROM FILM WHERE id = ?";
        Film film =  template.query(sql, mapper.getFilmRawMember(), id)
                .stream()
                .findAny()
                .orElse(null);
        if (film != null) {
            return film;
        } else {
            throw new IncorrectValuesException("User not found");
        }
    }

    private Film getByName(String name) {
        String sqlByName = "SELECT * FROM FILM WHERE name = ?;";
        return template.query(sqlByName, mapper.getFilmRawMember(), name).stream().findAny().orElse(null);
    }
}
