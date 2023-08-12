package ru.yandex.practicum.filmorate.storage.db;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.model.Genre;
import java.util.List;

@Component("GenreDbStorage")
public class GenreDbStorage {
    JdbcTemplate template;

    @Autowired
    public GenreDbStorage(JdbcTemplate template) {
        this.template = template;
    }

    public List<Genre> getAll() {
        String sql = "SELECT * FROM GENRE";
        return template.query(sql, new BeanPropertyRowMapper<>(Genre.class));
    }

    public Genre get(int id) throws IncorrectValuesException {
        String sql = "SELECT * FROM GENRE WHERE id = ?";
        Genre genre = template.query(sql, new BeanPropertyRowMapper<>(Genre.class), id)
                .stream()
                .findAny()
                .orElse(null);
        if (genre == null) {
            throw new IncorrectValuesException("Not found exception");
        }
        return genre;
    }
}
