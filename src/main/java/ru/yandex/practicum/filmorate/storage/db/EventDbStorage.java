package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mapper.EventRowMapper;
import ru.yandex.practicum.filmorate.model.Event;

import java.util.List;

@Slf4j
@Component("EventDbStorage")
public class EventDbStorage {
    private final JdbcTemplate template;
    private EventRowMapper mapper;

    @Autowired
    public EventDbStorage(JdbcTemplate template, EventRowMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    public List<Event> getFeed(int id) {
        String sql = "SELECT * FROM USER_FEED WHERE id = ?";
        return template.query(sql, mapper.getEventRowMapper(), id);
    }

    public void add(Event event) {
        log.info("add event into database");
        String sql = "INSERT INTO USER_FEED (id, timestamp, user_id, event_type, operation, entity_id) VALUES (?, ?, ?, ?, ?, ?)";
        template.update(sql,
                event.getEventId(),
                event.getTimestamp(),
                event.getUserId(),
                event.getEventType().name(),
                event.getOperation().name(),
                event.getEntityId()
        );
    }
}