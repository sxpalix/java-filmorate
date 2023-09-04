package ru.yandex.practicum.filmorate.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.model.enums.EventType;
import ru.yandex.practicum.filmorate.model.enums.Operation;
import ru.yandex.practicum.filmorate.service.event.db.DbEventService;
import ru.yandex.practicum.filmorate.storage.db.ReviewDbStorage;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EventDbTest {
    private final DbEventService dbEventService;
    private final ReviewDbStorage reviewDbStorage;

    @Test
    public void testCreateEventReview() {
        Review review = new Review();
        review.setUserId(1);
        review.setReviewId(101);

        Event event = dbEventService.createEventReview(review, Operation.ADD);

        assertEquals(System.currentTimeMillis(), event.getTimestamp(), 1000);
        assertEquals(1, event.getUserId());
        assertEquals(EventType.REVIEW, event.getEventType());
        assertEquals(Operation.ADD, event.getOperation());
        assertEquals(101, event.getEntityId());
    }

    @Test
    public void testUpdateEventReview() {
        Review review = new Review();
        review.setUserId(1);
        review.setReviewId(101);

        Event event = dbEventService.createEventReview(review, Operation.ADD);

        assertEquals(System.currentTimeMillis(), event.getTimestamp(), 1000);
        assertEquals(1, event.getUserId());
        assertEquals(EventType.REVIEW, event.getEventType());
        assertEquals(Operation.ADD, event.getOperation());
        assertEquals(101, event.getEntityId());

        event = dbEventService.createEventReview(review, Operation.UPDATE);

        assertEquals(System.currentTimeMillis(), event.getTimestamp(), 1000);
        assertEquals(1, event.getUserId());
        assertEquals(EventType.REVIEW, event.getEventType());
        assertEquals(Operation.UPDATE, event.getOperation());
        assertEquals(101, event.getEntityId());
    }

    @Test
    public void testDeleteEventReview() {
        Review review = new Review();
        review.setUserId(1);
        review.setReviewId(101);

        Event event = dbEventService.createEventReview(review, Operation.ADD);
        assertEquals(System.currentTimeMillis(), event.getTimestamp(), 1000);
        assertEquals(1, event.getUserId());
        assertEquals(EventType.REVIEW, event.getEventType());
        assertEquals(Operation.ADD, event.getOperation());
        assertEquals(101, event.getEntityId());

        event = dbEventService.createEventReview(review, Operation.REMOVE);
        assertEquals(System.currentTimeMillis(), event.getTimestamp(), 1000);
        assertEquals(1, event.getUserId());
        assertEquals(EventType.REVIEW, event.getEventType());
        assertEquals(Operation.REMOVE, event.getOperation());
        assertEquals(101, event.getEntityId());
    }

    @Test
    public void testCreateEventRemovelike() {
        int userId = 1;
        int filmId = 201;

        Event likeEvent = dbEventService.createEventLike(userId, filmId, Operation.ADD);

        assertEquals(System.currentTimeMillis(), likeEvent.getTimestamp(), 1000);
        assertEquals(userId, likeEvent.getUserId());
        assertEquals(EventType.LIKE, likeEvent.getEventType());
        assertEquals(Operation.ADD, likeEvent.getOperation());
        assertEquals(filmId, likeEvent.getEntityId());

        Event unlikeEvent = dbEventService.createEventLike(userId, filmId, Operation.REMOVE);

        assertEquals(System.currentTimeMillis(), unlikeEvent.getTimestamp(), 1000);
        assertEquals(userId, unlikeEvent.getUserId());
        assertEquals(EventType.LIKE, unlikeEvent.getEventType());
        assertEquals(Operation.REMOVE, unlikeEvent.getOperation());
        assertEquals(filmId, unlikeEvent.getEntityId());
    }

    @Test
    public void testCreateEventLike() {
        int userId = 1;
        int filmId = 201;

        Event event = dbEventService.createEventLike(userId, filmId, Operation.ADD);

        assertEquals(System.currentTimeMillis(), event.getTimestamp(), 1000);
        assertEquals(userId, event.getUserId());
        assertEquals(EventType.LIKE, event.getEventType());
        assertEquals(Operation.ADD, event.getOperation());
        assertEquals(filmId, event.getEntityId());
    }

    @Test
    public void testCreateEventAddFriend() {
        int userId = 1;
        int friendId = 301;

        Event event = dbEventService.createEventFriend(userId, friendId, Operation.ADD);

        assertEquals(System.currentTimeMillis(), event.getTimestamp(), 1000);
        assertEquals(userId, event.getUserId());
        assertEquals(EventType.FRIEND, event.getEventType());
        assertEquals(Operation.ADD, event.getOperation());
        assertEquals(friendId, event.getEntityId());
    }

    @Test
    public void testCreateEventRemoveFriend() {
        int userId = 1;
        int friendId = 301;

        Event addFriendEvent = dbEventService.createEventFriend(userId, friendId, Operation.ADD);

        assertEquals(System.currentTimeMillis(), addFriendEvent.getTimestamp(), 1000);
        assertEquals(userId, addFriendEvent.getUserId());
        assertEquals(EventType.FRIEND, addFriendEvent.getEventType());
        assertEquals(Operation.ADD, addFriendEvent.getOperation());
        assertEquals(friendId, addFriendEvent.getEntityId());

        Event removeFriendEvent = dbEventService.createEventFriend(userId, friendId, Operation.REMOVE);

        assertEquals(System.currentTimeMillis(), removeFriendEvent.getTimestamp(), 1000);
        assertEquals(userId, removeFriendEvent.getUserId());
        assertEquals(EventType.FRIEND, removeFriendEvent.getEventType());
        assertEquals(Operation.REMOVE, removeFriendEvent.getOperation());
        assertEquals(friendId, removeFriendEvent.getEntityId());
    }
}