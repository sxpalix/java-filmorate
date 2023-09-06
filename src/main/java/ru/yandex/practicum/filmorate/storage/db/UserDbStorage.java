package ru.yandex.practicum.filmorate.storage.db;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.sql.PreparedStatement;
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

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO Users(name, email, birthday, login) VALUES (?, ?, ?, ?);";
        template.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql,  new String[]{"id"});
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setDate(3, Date.valueOf(user.getBirthday()));
            stmt.setString(4, user.getLogin());

            return stmt;
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());

        return getById(user.getId());
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

    @Override
    public void delete(User user) throws IncorrectValuesException {
        log.info("delete user from database by id");
        if (user == null) {
            log.info("NULL can not find user");
            throw new IncorrectValuesException("User not found");
        } else {
            String sql = "DELETE FROM USERS WHERE ID = ?";
            template.update(sql, user.getId());
        }
    }

    private User getById(int id) {
        log.info("get user from database by id");
        String sql = "SELECT * FROM Users WHERE ID = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(User.class), id)
                .stream()
                .findAny()
                .orElse(null);
    }
}
