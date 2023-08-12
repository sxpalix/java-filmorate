package ru.yandex.practicum.filmorate.service.dbService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.Storage;
import java.util.List;

@Service("DbUserService")
@Qualifier("DbUserService")
public class DbUserService implements UserService {
    Storage<User> storage;
    JdbcTemplate template;

    public DbUserService(@Qualifier("UserDbStorage") Storage<User> storage, JdbcTemplate template) {
        this.storage = storage;
        this.template = template;
    }

    private void validations(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    @Override
    public void putToFriends(int id, int friendsId) throws IncorrectValuesException {
        getUserById(id);;
        getUserById(friendsId);
        String sql = "INSERT INTO FRIENDSHIP(user_id, friend_id) VALUES(?, ?)";
        template.update(sql, id, friendsId);
    }

    @Override
    public void unfriending(int id, int friendsId) {
        String sql = "DELETE FROM FRIENDSHIP WHERE user_id = ? AND friend_id = ?";
        template.update(sql, id, friendsId);
    }

    @Override
    public List<User> listOfMutualFriends(int id, int friendsId) throws IncorrectValuesException {
        String sql = "SELECT u.id,u.name, u.email, u.birthday, u.login\n" +
                "                FROM (SELECT friend_id AS fr_id, COUNT(friend_id) AS c\n" +
                "                FROM (SELECT friend_id FROM FRIENDSHIP WHERE user_id = ? or user_id = ?)\n" +
                "                GROUP BY fr_id HAVING c > 1) AS fr INNER JOIN USERS AS u ON fr.fr_id = u.id";
        storage.get(id);
        storage.get(friendsId);
        return template.query(sql, new BeanPropertyRowMapper<>(User.class), id, friendsId);
    }

    @Override
    public List<User> getFriendsList(int id) throws IncorrectValuesException {
        String sql = "SELECT u.id, u.name, u.email, u.birthday, u.login " +
                "FROM (SELECT friend_id AS id FROM FRIENDSHIP WHERE user_id = ?) " +
                "AS friendship INNER JOIN USERS AS u ON friendship.id = u.id;";
        storage.get(id);
        return template.query(sql, new BeanPropertyRowMapper<>(User.class), id);
    }

    @Override
    public List<User> getUsers() {
        return storage.getAll();
    }

    @Override
    public User postUser(User user) throws ValidationException, IncorrectValuesException {
        validations(user);
        return storage.post(user);
    }

    @Override
    public User putUser(User user) throws ValidationException, IncorrectValuesException {
        validations(user);
        return storage.put(user);
    }

    @Override
    public User getUserById(int id) throws IncorrectValuesException {
        return storage.get(id);
    }
}
