package ru.yandex.practicum.filmorate.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storage.db.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.db.ReviewDbStorage;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ReviewDbTest {
    private final ReviewDbStorage reviewDbStorage;
    private final UserDbStorage userDbStorage;
    private final FilmDbStorage filmDbStorage;
    private static User user;
    private static Film film;
    private static final Mpa mpa = new Mpa(5, "NC-17");

    @BeforeAll
    public static void beforeAll() {
        user = createUserForTest(1);
        film = createFilmForTest();
    }

    @BeforeEach
    public void beforeEach() throws ValidationException, IncorrectValuesException {
        userDbStorage.post(user);
        filmDbStorage.post(film);
    }

    @Test
    public void shouldCreateReview() throws ValidationException, IncorrectValuesException {
        Review review = createReviewForTest();
        reviewDbStorage.post(review);

        assertEquals(review, reviewDbStorage.get(review.getReviewId()));
    }

    @Test
    public void shouldNotCreateReview() {
        Review review = createReviewForTest();
        review.setFilmId(9999);

        assertThrows(DataIntegrityViolationException.class, () -> reviewDbStorage.post(review));

        review.setFilmId(1);
        review.setUserId(9999);

        assertThrows(DataIntegrityViolationException.class, () -> reviewDbStorage.post(review));
    }

    @Test
    public void shouldReadReview() throws ValidationException, IncorrectValuesException {
        Review reviewActual = createReviewForTest();
        reviewDbStorage.post(reviewActual);
        Review reviewExpected = reviewDbStorage.get(1);

        assertEquals(reviewExpected, reviewActual);
    }

    @Test
    public void shouldNotReadReview() {
        assertThrows(IncorrectValuesException.class, () -> reviewDbStorage.get(-1));
    }

    @Test
    public void shouldUpdateReview() throws ValidationException, IncorrectValuesException {
        Review review = createReviewForTest();
        reviewDbStorage.post(review);
        review.setContent("updated review");

        assertEquals(review, reviewDbStorage.put(review));
    }

    @Test
    public void shouldNotUpdateReview() throws ValidationException, IncorrectValuesException {
        Review review = createReviewForTest();
        reviewDbStorage.post(review);
        review.setFilmId(-9999);

        assertThrows(DataIntegrityViolationException.class, () -> reviewDbStorage.put(review));

        review.setFilmId(1);
        review.setUserId(9999);

        assertThrows(DataIntegrityViolationException.class, () -> reviewDbStorage.post(review));
    }

    @Test
    public void shouldDeleteReview() throws ValidationException, IncorrectValuesException {
        Review review = createReviewForTest();
        reviewDbStorage.post(review);
        reviewDbStorage.delete(review);

        assertThrows(IncorrectValuesException.class, () -> reviewDbStorage.get(review.getReviewId()));
    }

    @Test
    public void shouldNotSendErrorWhenDeleteReview() {
        Review review = createReviewForTest();
        assertDoesNotThrow(() -> reviewDbStorage.delete(review));
    }

    @Test
    public void shouldDislikeReview() throws IncorrectValuesException, ValidationException {
        Review review = createReviewForTest();
        User disLiker = createUserForTest(2);
        userDbStorage.post(disLiker);
        reviewDbStorage.post(review);
        reviewDbStorage.addDislike(review.getReviewId(), disLiker.getId());

        review.setDislikes(List.of(disLiker.getId()));
        review.setUseful(-1);

        assertEquals(review, reviewDbStorage.get(review.getReviewId()));
    }

    @Test
    public void shouldNotDislikeReview() {
        assertThrows(DataIntegrityViolationException.class,
                () -> reviewDbStorage.addDislike(-9999, 9999));

        Review review = createReviewForTest();

        assertThrows(DataIntegrityViolationException.class,
                () -> reviewDbStorage.addDislike(review.getReviewId(), 9999));
    }

    @Test
    public void shouldDeleteDislikeFromReview() throws IncorrectValuesException, ValidationException {
        Review review = createReviewForTest();
        User unDisLiker = createUserForTest(2);
        userDbStorage.post(unDisLiker);
        reviewDbStorage.post(review);
        reviewDbStorage.addDislike(review.getReviewId(), unDisLiker.getId());
        reviewDbStorage.deleteDislike(review.getReviewId(), unDisLiker.getId());

        assertEquals(review, reviewDbStorage.get(review.getReviewId()));
    }

    @Test
    public void shouldNotSendErrorWhenDeleteDislikeFromReview() {
        assertDoesNotThrow(() -> reviewDbStorage.deleteDislike(-9999, 9999));

        Review review = createReviewForTest();

        assertDoesNotThrow(() -> reviewDbStorage.deleteDislike(review.getReviewId(), 9999));
    }

    @Test
    public void shouldLikeReview() throws IncorrectValuesException, ValidationException {
        Review review = createReviewForTest();
        User liker = createUserForTest(2);
        userDbStorage.post(liker);
        reviewDbStorage.post(review);
        reviewDbStorage.addLike(review.getReviewId(), liker.getId());

        review.setLikes(List.of(liker.getId()));
        review.setUseful(1);

        assertEquals(review, reviewDbStorage.get(review.getReviewId()));
    }

    @Test
    public void shouldNotLikeReview() {
        assertThrows(DataIntegrityViolationException.class,
                () -> reviewDbStorage.addLike(-9999, 9999));

        Review review = createReviewForTest();

        assertThrows(DataIntegrityViolationException.class,
                () -> reviewDbStorage.addLike(review.getReviewId(), 9999));
    }

    @Test
    public void shouldDeleteLikeFromReview() throws IncorrectValuesException, ValidationException {
        Review review = createReviewForTest();
        User unLiker = createUserForTest(2);
        userDbStorage.post(unLiker);
        reviewDbStorage.post(review);
        reviewDbStorage.addLike(review.getReviewId(), unLiker.getId());
        reviewDbStorage.deleteLike(review.getReviewId(), unLiker.getId());

        assertEquals(review, reviewDbStorage.get(review.getReviewId()));
    }

    @Test
    public void shouldNotSendErrorWhenDeleteLikeFromReview() {
        assertDoesNotThrow(() -> reviewDbStorage.deleteLike(-9999, 9999));

        Review review = createReviewForTest();

        assertDoesNotThrow(() -> reviewDbStorage.deleteLike(review.getReviewId(), 9999));
    }

    private Review createReviewForTest() {
        Review review = new Review(
                1,
                "Test review id=" + 1,
                true,
                user.getId(),
                film.getId()
        );
        review.setLikes(Collections.emptyList());
        review.setDislikes(Collections.emptyList());

        return review;
    }

    private static User createUserForTest(int id) {
        return new User(
                id,
                "user" + id + "@gmail.kz",
                "user" + id + "Login",
                "user" + id + "Name",
                LocalDate.parse("2000-01-0" + id)
        );
    }

    private static Film createFilmForTest() {
        Film film = new Film();
        film.setId(1);
        film.setName("film for review");
        film.setDescription("film for test");
        film.setReleaseDate(LocalDate.parse("2000-01-01"));
        film.setDuration(250);
        film.setMpa(mpa);

        return film;
    }
}
