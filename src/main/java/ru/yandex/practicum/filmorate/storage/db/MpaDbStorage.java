package ru.yandex.practicum.filmorate.storage.db;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.model.Mpa;
import java.util.List;

@Component("MpaDbStorage")
public class MpaDbStorage {
    JdbcTemplate template;
    @Autowired
    public MpaDbStorage(JdbcTemplate template) {
        this.template =template;
    }

    public List<Mpa> getAll() {
        String sql = "SELECT * FROM MPA";
        return template.query(sql, new BeanPropertyRowMapper<>(Mpa.class));
    }

    public Mpa get(int id) throws IncorrectValuesException {
        String sql = "SELECT * FROM MPA WHERE id = ?";
        Mpa mpa =  template.query(sql, new BeanPropertyRowMapper<>(Mpa.class), id)
                .stream()
                .findAny()
                .orElse(null);
        if (mpa == null) {
            throw new IncorrectValuesException("Not found");
        }
        return mpa;
    }
}