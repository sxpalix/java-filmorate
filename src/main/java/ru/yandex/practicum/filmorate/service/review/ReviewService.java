package ru.yandex.practicum.filmorate.service.review;

import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

public interface ReviewService {
    Review put(Review review) throws ValidationException, IncorrectValuesException;

    List<Review> getAll(Integer filmId, Integer count);

    Review post(Review review) throws ValidationException, IncorrectValuesException;

    Review getById(int id) throws IncorrectValuesException;

    void deleteById(Review review) throws IncorrectValuesException;

    Review addLike(int reviewId, int userId) throws IncorrectValuesException;

    Review addDislike(int reviewId, int userId) throws IncorrectValuesException;

    Review deleteLike(int reviewId, int userId) throws IncorrectValuesException;

    Review deleteDislike(int reviewId, int userId) throws IncorrectValuesException;

    List<Review> getByFilmId(int filmId, int count);
}
