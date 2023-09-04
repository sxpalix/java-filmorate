package ru.yandex.practicum.filmorate.service.userLike.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.enums.Operation;
import ru.yandex.practicum.filmorate.service.event.db.DbEventService;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.service.userLike.UserLikeService;

import java.util.List;

@Slf4j
@Service
public class DbUserLikeService implements UserLikeService {
    private final UserService service;
    private final JdbcTemplate template;

    private final DbEventService dbEventService;

    @Autowired
    public DbUserLikeService(@Qualifier("DbUserService") UserService service, JdbcTemplate template, DbEventService dbEventService) {
        this.service = service;
        this.template = template;
        this.dbEventService = dbEventService;
    }

    @Override
    public void putToFriends(int id, int friendsId) throws IncorrectValuesException {
        log.info("put user with {} id to user with {} id as friends", id, friendsId);
        service.get(id);
        service.get(friendsId);
        String sql = "INSERT INTO FRIENDSHIP(user_id, friend_id) VALUES(?, ?)";
        template.update(sql, id, friendsId);

        dbEventService.add(dbEventService.createEventFriend(id, friendsId, Operation.ADD));
    }

    @Override
    public void unfriending(int id, int friendsId) {
        log.info("delete user with {} id to user with {} id as friends", id, friendsId);
        String sql = "DELETE FROM FRIENDSHIP WHERE user_id = ? AND friend_id = ?";
        template.update(sql, id, friendsId);
        dbEventService.add(dbEventService.createEventFriend(id, friendsId, Operation.REMOVE));
    }

    @Override
    public List<User> listOfMutualFriends(int id, int friendsId) throws IncorrectValuesException {
        log.info("return common friends at user with id {}, {}", id, friendsId);
        String sql = "SELECT u.id,u.name, u.email, u.birthday, u.login\n" +
                "                FROM (SELECT friend_id AS fr_id, COUNT(friend_id) AS c\n" +
                "                FROM (SELECT friend_id FROM FRIENDSHIP WHERE user_id = ? or user_id = ?)\n" +
                "                GROUP BY fr_id HAVING c > 1) AS fr INNER JOIN USERS AS u ON fr.fr_id = u.id";
        service.get(id);
        service.get(friendsId);
        return template.query(sql, new BeanPropertyRowMapper<>(User.class), id, friendsId);
    }

    @Override
    public List<User> getFriendsList(int id) throws IncorrectValuesException {
        log.info("return friends list at user with id = {}", id);
        String sql = "SELECT u.id, u.name, u.email, u.birthday, u.login " +
                "FROM (SELECT friend_id AS id FROM FRIENDSHIP WHERE user_id = ?) " +
                "AS friendship INNER JOIN USERS AS u ON friendship.id = u.id;";
        service.get(id);
        return template.query(sql, new BeanPropertyRowMapper<>(User.class), id);
    }
}
