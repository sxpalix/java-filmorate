package ru.yandex.practicum.filmorate.mapper;

import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.enums.Operation;
import ru.yandex.practicum.filmorate.model.enums.EventType;

@Component
public class EventRowMapper {
    JdbcTemplate template;

    public EventRowMapper(JdbcTemplate template) {
        this.template = template;
    }

    @Getter
    RowMapper<Event> eventRowMapper = (resultSet, rowNum) -> {
        Event event = new Event();
        event.setEventId(resultSet.getInt("id"));
        event.setTimestamp(resultSet.getTimestamp("timestamp").getTime());
        event.setUserId(resultSet.getInt("user_id"));
        event.setEventType(EventType.valueOf(resultSet.getString("event_type")));
        event.setOperation(Operation.valueOf(resultSet.getString("operation")));
        event.setEntityId(resultSet.getInt("entity_id"));
        return event;
    };
}