package ru.yandex.practicum.filmorate.storage.db;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.storage.validation.Valid;
import java.util.List;

@Slf4j
@Component("FilmDbStorage")
public class FilmDbStorage implements Storage<Film> {
    JdbcTemplate template;
    GenreDbStorage GenreDbStorage;
    MpaDbStorage mpaDbStorage;
    Valid<Film> valid;
    FilmRowMapper mapper;
    @Autowired
    public FilmDbStorage(JdbcTemplate template, @Qualifier("GenreDbStorage") GenreDbStorage GenreDbStorage,
                         MpaDbStorage mpaDbStorage, Valid<Film> valid, FilmRowMapper mapper) {
        this.template =template;
        this.GenreDbStorage = GenreDbStorage;
        this.mpaDbStorage = mpaDbStorage;
        this.valid = valid;
        this.mapper = mapper;
    }

    @Override
    public Film put(Film film) throws ValidationException, IncorrectValuesException {
        valid.validations(film);
        String sql = "UPDATE FILM SET NAME=?, DESCRIPTION=?, release_date=?, duration=?, mpa_id=?, rating=?" +
                "  WHERE ID=?";
        template.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId(), film.getRating(), film.getId());
        template.update("DELETE FROM FILM_GENRES WHERE FILM_ID=?", film.getId());
        String saveFilmGenre = "INSERT INTO FILM_GENRES(FILM_ID, GENRE_ID) VALUES (?, ?)";
        int id = film.getId();
        for (int i = 0; i < film.getGenres().size(); i++) {
            template.update(saveFilmGenre, id, film.getGenres().get(i).getId());
        }
        return get(film.getId());
    }

    @Override
    public List<Film> getAll() {
        String sql = "SELECT * FROM FILM";
        return template.query(sql, mapper.getFilmRawMember());
    }

    @Override
    public Film post(Film film) throws ValidationException {
        valid.validations(film);
        String sql = "INSERT INTO FILM(id, name, description, release_date, duration, mpa_id, rating)" +
                " VALUES (DEFAULT, ?, ?, ?, ?, ?, ?);";
        template.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId(), film.getRating());
        String saveFilmGenre = "INSERT INTO FILM_GENRES(FILM_ID, GENRE_ID) VALUES (?, ?)";
        int id = getByName(film.getName()).getId();
        for (int i = 0; i < film.getGenres().size(); i++) {
            template.update(saveFilmGenre, id, film.getGenres().get(i).getId());
        }
        return getByName(film.getName());
    }

    @Override
    public Film get(int id) throws IncorrectValuesException {
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
