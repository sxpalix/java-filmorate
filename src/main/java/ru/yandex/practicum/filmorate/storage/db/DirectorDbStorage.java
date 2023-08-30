package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;

@Slf4j
@Component("DirectorDbStorage")
public class DirectorDbStorage {
    private final JdbcTemplate template;

    @Autowired
    public DirectorDbStorage(JdbcTemplate template) {
        this.template = template;
    }

    public Director put(Director director) throws ValidationException, IncorrectValuesException {
        validation(director.getName());
        log.info("put DIRECTOR into database");
        String sql = "UPDATE DIRECTOR SET NAME=? WHERE ID=?";
        template.update(sql, director.getName(), director.getId());
        return get(director.getId());
    }

    public List<Director> getAll() {
        log.info("get Director list");
        String sql = "SELECT * FROM DIRECTOR";
        return template.query(sql, new BeanPropertyRowMapper<>(Director.class));
    }

    public Director post(Director director) throws ValidationException, IncorrectValuesException {
        validation(director.getName());
        log.info("post DIRECTOR into database");
        String sql = "INSERT INTO DIRECTOR(id, name) VALUES (DEFAULT, ?);";
        template.update(sql, director.getName());
        return getByName(director.getName());
    }

    public Director get(int id) throws IncorrectValuesException {
        log.info("get Director by id");
        String sql = "SELECT * FROM DIRECTOR WHERE id = ?";
        Director director = template.query(sql, new BeanPropertyRowMapper<>(Director.class), id)
                .stream()
                .findAny()
                .orElse(null);
        if (director == null) {
            throw new IncorrectValuesException("Not found exception");
        }
        return director;
    }

    public void delete(int id) {
        String sqlFilmDirector = "DELETE FROM FILM_DIRECTOR WHERE DIRECTOR_ID = ?;";
        String sql = "DELETE FROM DIRECTOR WHERE id = ?;";
        template.update(sqlFilmDirector, id);
        template.update(sql, id);
    }

    private Director getByName(String name) {
        String sqlByName = "SELECT * FROM DIRECTOR WHERE name = ?;";
        return template.query(sqlByName, new BeanPropertyRowMapper<>(Director.class), name)
                .stream().findAny().orElse(null);
    }

    private void validation(String name) throws ValidationException {
        if (name.isBlank()) {
            throw new ValidationException("Name couldn't be blank");
        }
    }
}
