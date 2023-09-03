package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.enums.EventType;
import ru.yandex.practicum.filmorate.model.enums.Operation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
public class EventDbStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EventDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Event> getFeed(int id) {
        log.info("get feed by id.");
        String sql = "SELECT * FROM USER_FEED WHERE USER_ID = ?";
        return jdbcTemplate.query(sql, this::buildEvent, id);
    }

    public void add(Event event) {
        log.info("add new event into database");

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO USER_FEED (USER_ID, TIME_STAMP, EVENT_TYPE, OPERATION, ENTITY_ID) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"EVENT_ID"});
            stmt.setInt(1, event.getUserId());
            stmt.setLong(2, event.getTimestamp());
            stmt.setString(3, event.getEventType().name());
            stmt.setString(4, event.getOperation().name());
            stmt.setInt(5, event.getEntityId());

            return stmt;
        }, keyHolder);
        event.setEventId(keyHolder.getKey().intValue());
    }

    private Event buildEvent(ResultSet resultSet, int rowNum) throws SQLException {
        return Event.builder()
                .eventId(resultSet.getInt("EVENT_ID"))
                .timestamp(resultSet.getLong("TIME_STAMP"))
                .userId(resultSet.getInt("USER_ID"))
                .eventType(EventType.valueOf(resultSet.getString("EVENT_TYPE")))
                .operation(Operation.valueOf(resultSet.getString("OPERATION")))
                .entityId(resultSet.getInt("ENTITY_ID"))
                .build();
    }
}