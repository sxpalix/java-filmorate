package ru.yandex.practicum.filmorate.storage.db;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

@Slf4j
@Component("UserDbStorage")
public class UserDbStorage implements Storage<User> {
    private final JdbcTemplate template;

    @Autowired
    public UserDbStorage(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public User put(User user) throws ValidationException, IncorrectValuesException {
        log.info("put user into database");
        String sql = "UPDATE USERS SET NAME=?, EMAIL=?, LOGIN=?, BIRTHDAY=? WHERE ID=?";
        template.update(sql, user.getName(), user.getEmail(), user.getLogin(), user.getBirthday(), user.getId());
        return get(user.getId());
    }

    @Override
    public List<User> getAll() {
        log.info("get all user from database");
        String sql = "SELECT * FROM USERS";
        return template.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User post(User user) throws ValidationException, IncorrectValuesException {
        log.info("post user into database");
        String sql = "INSERT INTO Users(id, name, email, birthday, login) VALUES (DEFAULT, ?, ?, ?, ?);";
        template.update(sql, user.getName(), user.getEmail(), user.getBirthday(), user.getLogin());
        return getByEmail(user.getEmail());
    }

    public User get(int id) throws IncorrectValuesException {
        log.info("get user from database by id");
        String sql = "SELECT * FROM USERS WHERE id = ?";
        User user =  template.query(sql, new BeanPropertyRowMapper<>(User.class), id)
                .stream()
                .findAny()
                .orElse(null);
        if (user != null) {
            return user;
        } else {
            throw new IncorrectValuesException("User not found");
        }
    }

    private User getByEmail(String email) {
        log.info("get user from database by id");
        String sqlByEmail = "SELECT * FROM Users WHERE EMAIL = ?";
        return template.query(sqlByEmail, new BeanPropertyRowMapper<>(User.class), email)
                .stream()
                .findAny()
                .orElse(null);
    }
}
