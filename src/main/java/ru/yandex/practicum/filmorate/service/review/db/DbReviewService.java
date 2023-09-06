package ru.yandex.practicum.filmorate.service.review.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.model.enums.Operation;
import ru.yandex.practicum.filmorate.service.event.db.DbEventService;
import ru.yandex.practicum.filmorate.service.film.db.DbFilmService;
import ru.yandex.practicum.filmorate.service.review.ReviewService;
import ru.yandex.practicum.filmorate.service.user.db.DbUserService;
import ru.yandex.practicum.filmorate.storage.db.ReviewDbStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("DbReviewService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DbReviewService implements ReviewService {
    private final ReviewDbStorage reviewStorage;
    private final DbFilmService filmService;
    private final DbUserService userService;
    private final DbEventService dbEventService;

    @Override
    public Review put(Review review) throws ValidationException, IncorrectValuesException {
        if (getById(review.getReviewId()) == null) {
            throw new IncorrectValuesException("Review not found");
        }
        Review newReview = reviewStorage.put(review);
        dbEventService.add(dbEventService.createEventReview(newReview, Operation.UPDATE));
        return newReview;
    }

    @Override
    public List<Review> getAll(Integer filmId, Integer count) {
        if (filmId == null || filmId < 1) {
            return reviewStorage.getAll().stream()
                    .sorted(Comparator.comparingLong(Review::getUseful).reversed())
                    .collect(Collectors.toList());
        } else if (count == null || count < 1) {
            return getByFilmId(filmId, 10);
        } else {
            return getByFilmId(filmId, count);
        }
    }

    @Override
    public Review post(Review review) throws ValidationException, IncorrectValuesException {
        if (filmService.getFilm(review.getFilmId()) == null || userService.getUserById(review.getUserId()) == null) {
            throw new IncorrectValuesException("Film or user not found");
        }
        Review newReview = reviewStorage.post(review);
        dbEventService.add(dbEventService.createEventReview(newReview, Operation.ADD));
        return newReview;
    }

    @Override
    public Review getById(int id) throws IncorrectValuesException {
        return reviewStorage.get(id);
    }

    @Override
    public void deleteById(Review review) throws IncorrectValuesException {
        dbEventService.add(dbEventService.createEventReview(review, Operation.REMOVE));
        reviewStorage.delete(review);
    }

    @Override
    public Review addLike(int reviewId, int userId) throws IncorrectValuesException {
        checkReviewUser(reviewId, userId);
        reviewStorage.addLike(reviewId, userId);
        return reviewStorage.get(reviewId);
    }

    @Override
    public Review addDislike(int reviewId, int userId) throws IncorrectValuesException {
        checkReviewUser(reviewId, userId);
        reviewStorage.addDislike(reviewId, userId);
        return reviewStorage.get(reviewId);
    }

    @Override
    public Review deleteLike(int reviewId, int userId) throws IncorrectValuesException {
        checkReviewUser(reviewId, userId);
        reviewStorage.deleteLike(reviewId, userId);
        return reviewStorage.get(reviewId);
    }

    @Override
    public Review deleteDislike(int reviewId, int userId) throws IncorrectValuesException {
        checkReviewUser(reviewId, userId);
        reviewStorage.deleteDislike(reviewId, userId);
        return reviewStorage.get(reviewId);
    }

    @Override
    public List<Review> getByFilmId(int filmId, int count) {
        return reviewStorage.getByFilmId(filmId).stream()
                .sorted(Comparator.comparingLong(Review::getUseful).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    private void checkReviewUser(int reviewId, int userId) throws IncorrectValuesException {
        if (getById(reviewId) == null || userService.getUserById(userId) == null) {
            throw new IncorrectValuesException("Review or user not found");
        }
    }
}
