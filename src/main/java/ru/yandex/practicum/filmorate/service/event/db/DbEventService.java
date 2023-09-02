package ru.yandex.practicum.filmorate.service.event.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.service.event.EventService;
import ru.yandex.practicum.filmorate.storage.db.EventDbStorage;

import java.util.List;

@Slf4j
@Service
public class DbEventService implements EventService {
    private EventDbStorage storage;

    public DbEventService(EventDbStorage storage) {
        this.storage = storage;
    }

    @Override
    public List<Event> getFeed(int id) {
        return storage.getFeed(id);
    }
}
