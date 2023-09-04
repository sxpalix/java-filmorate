package ru.yandex.practicum.filmorate.service.event.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.model.enums.EventType;
import ru.yandex.practicum.filmorate.model.enums.Operation;
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
    public List<Event> getFeed(int id) throws ValidationException, IncorrectValuesException {
        return storage.getFeed(id);
    }

    @Override
    public void add(Event event) {
        storage.add(event);
    }

    public Event createEventReview(Review review, Operation operation) {
        return Event.builder()
                .timestamp(System.currentTimeMillis())
                .userId(review.getUserId())
                .eventType(EventType.REVIEW)
                .operation(operation)
                .entityId(review.getReviewId())
                .build();
    }

    public Event createEventLike(int userId, int filmId, Operation operation) {
        return Event.builder()
                .timestamp(System.currentTimeMillis())
                .userId(userId)
                .eventType(EventType.LIKE)
                .operation(operation)
                .entityId(filmId)
                .build();
    }

    public Event createEventFriend(int userId, int friendId, Operation operation) {
        return Event.builder()
                .timestamp(System.currentTimeMillis())
                .userId(userId)
                .eventType(EventType.FRIEND)
                .operation(operation)
                .entityId(friendId)
                .build();
    }
}
