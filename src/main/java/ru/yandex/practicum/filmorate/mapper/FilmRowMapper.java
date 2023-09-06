package ru.yandex.practicum.filmorate.mapper;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.db.MpaDbStorage;

import java.util.LinkedHashSet;
import java.util.List;

@Component
public class FilmRowMapper {
    private MpaDbStorage mpaDbStorage;
    private final JdbcTemplate template;

    @Autowired
    public FilmRowMapper(MpaDbStorage mpaDbStorage, JdbcTemplate template) {
        this.mpaDbStorage = mpaDbStorage;
        this.template = template;
    }

    @Getter
    RowMapper<Film> filmRawMember = (resultSet, rowNum) -> {
        Film film = new Film();
        film.setId(resultSet.getInt("id"));
        film.setName(resultSet.getString("name"));
        film.setDescription(resultSet.getString("description"));
        film.setReleaseDate(resultSet.getDate("release_Date").toLocalDate());
        film.setDuration(resultSet.getInt("duration"));
        film.setRating(resultSet.getDouble("rating"));
        try {
            film.setMpa(mpaDbStorage.get(resultSet.getInt("mpa_id")));
        } catch (IncorrectValuesException e) {
            throw new RuntimeException(e);
        }
        film.setGenres(new LinkedHashSet<>(getGenres(film)));
        film.setDirectors(new LinkedHashSet<>(getDirector(film)));
        return film;
    };

    private List<Genre> getGenres(Film film) {
        return template.query("SELECT DISTINCT id, name FROM Genre AS gen INNER JOIN\n" +
                "(SELECT genre_id FROM FILM_GENRES WHERE film_id = ?) AS g ON gen.id = g.genre_id;",
                new BeanPropertyRowMapper<>(Genre.class), film.getId());
    }

    private List<Director> getDirector(Film film) {
        return template.query("SELECT DISTINCT id, name FROM DIRECTOR AS dir INNER JOIN\n" +
                        "(SELECT director_id FROM FILM_DIRECTOR WHERE film_id = ?) AS d ON dir.id = d.director_id;",
                new BeanPropertyRowMapper<>(Director.class), film.getId());
    }
}
