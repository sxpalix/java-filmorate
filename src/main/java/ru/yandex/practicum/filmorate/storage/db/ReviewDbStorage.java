package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Review;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Component("ReviewDbStorage")
public class ReviewDbStorage {
    private final JdbcTemplate template;

    @Autowired
    public ReviewDbStorage(JdbcTemplate template) {
        this.template = template;
    }

    public List<Review> getAll() {
        log.info("get all review");

        String sql = "SELECT * FROM REVIEWS";
        return template.query(sql, this::buildReview);
    }

    public Review get(int id) throws IncorrectValuesException {
        log.info("get review by id");

        String sql = "SELECT * FROM REVIEWS WHERE REVIEW_ID = ?";
        Review review = template.query(sql, this::buildReview, id).stream()
                .findAny()
                .orElse(null);

        if (review != null) {
            return review;
        } else {
            throw new IncorrectValuesException("Review not found");
        }
    }

    public Review post(Review review) throws ValidationException, IncorrectValuesException {
        log.info("post new review");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO REVIEWS (content, is_positive, user_id, film_id) VALUES (?, ?, ?, ?)";

        template.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"review_id"});

            stmt.setString(1, review.getContent());
            stmt.setBoolean(2, review.getIsPositive());
            stmt.setInt(3, review.getUserId());
            stmt.setInt(4, review.getFilmId());

            return stmt;
        }, keyHolder);

        review.setReviewId(keyHolder.getKey().intValue());
        return getById(review.getReviewId());
    }

    public Review put(Review review) throws ValidationException, IncorrectValuesException {
        log.info("put new review");

        String sql = "UPDATE REVIEWS SET content = ?, is_positive = ? WHERE REVIEW_ID = ?";
        template.update(sql, review.getContent(), review.getIsPositive(), review.getReviewId());

        return get(review.getReviewId());
    }

    public void delete(Review review) throws IncorrectValuesException {
        log.info("delete review by id");

        if (review == null) {
            throw new IncorrectValuesException("Review not found");
        } else {
            String sql = "DELETE FROM REVIEWS WHERE REVIEW_ID = ?";
            template.update(sql, review.getReviewId());
        }
    }

    public void addLike(int reviewId, int userId) {
        log.info("add like to review");

        String sql = "INSERT INTO LIKE_REVIEWS (review_id, user_id) VALUES(?, ?)";

        if (checkDislike(reviewId, userId)) {
            deleteDislike(reviewId, userId);
        }

        template.update(sql, reviewId, userId);
    }

    public void deleteLike(int reviewId, int userId) {
        log.info("delete like from review");

        String sql = "DELETE FROM LIKE_REVIEWS WHERE REVIEW_ID = ? AND USER_ID = ?";
        template.update(sql, reviewId, userId);
    }

    public void addDislike(int reviewId, int userId) {
        log.info("add dislike to review");

        String sql = "INSERT INTO DISLIKE_REVIEWS (review_id, user_id) VALUES(?, ?)";

        if (checkLike(reviewId, userId)) {
            deleteLike(reviewId, userId);
        }

        template.update(sql, reviewId, userId);
    }

    public void deleteDislike(int reviewId, int userId) {
        log.info("delete dislike from review");

        String sql = "DELETE FROM DISLIKE_REVIEWS WHERE REVIEW_ID = ? AND USER_ID = ?";
        template.update(sql, reviewId, userId);
    }

    public List<Review> getByFilmId(int filmId) { // получаем все отзывы к фильму
        String sqlQuery = "SELECT * FROM REVIEWS WHERE FILM_ID = ?;";
        return template.query(sqlQuery, this::buildReview, filmId);
    }

    private Review buildReview(ResultSet resultSet, int rowNum) throws SQLException {
        Review review = new Review(
                resultSet.getInt("review_id"),
                resultSet.getString("content"),
                resultSet.getBoolean("is_positive"),
                resultSet.getInt("user_id"),
                resultSet.getInt("film_id"));

        review.setLikes(getReviewLikesFromTable(review.getReviewId()));
        review.setDislikes(getReviewDislikesFromTable(review.getReviewId()));
        review.setUseful(calculateUseful(review));
        return review;
    }

    private int calculateUseful(Review review) {
        int useful = 0;
        useful += review.getLikes().size();
        useful -= review.getDislikes().size();
        return useful;
    }

    private List<Integer> getReviewLikesFromTable(Integer reviewId) {
        String sqlQuery = "SELECT USER_ID FROM LIKE_REVIEWS WHERE REVIEW_ID = ?;";
        return template.query(sqlQuery, this::getUserIdFromTableReviewLikes, reviewId);
    }

    private int getUserIdFromTableReviewLikes(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getInt("user_id");
    }

    private List<Integer> getReviewDislikesFromTable(Integer reviewId) {
        String sqlQuery = "SELECT USER_ID FROM DISLIKE_REVIEWS WHERE REVIEW_ID = ?;";
        return template.query(sqlQuery, this::getUserIdFromTableReviewDislikes, reviewId);
    }

    private int getUserIdFromTableReviewDislikes(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getInt("user_id");
    }

    private boolean checkDislike(int reviewId, int userId) {
        String sql = "SELECT * FROM DISLIKE_REVIEWS WHERE REVIEW_ID = ? AND USER_ID = ?";
        Review review = template.query(sql, new BeanPropertyRowMapper<>(Review.class), reviewId, userId)
                .stream()
                .findAny()
                .orElse(null);

        return review != null;
    }

    private boolean checkLike(int reviewId, int userId) {
        String sql = "SELECT * FROM LIKE_REVIEWS WHERE REVIEW_ID = ? AND USER_ID = ?";
        Review review = template.query(sql, new BeanPropertyRowMapper<>(Review.class), reviewId, userId)
                .stream()
                .findAny()
                .orElse(null);

        return review != null;
    }

    private Review getById(int id) {
        log.info("get review from database by id");
        String sqlByEmail = "SELECT * FROM REVIEWS WHERE REVIEW_ID = ?";
        return template.query(sqlByEmail, new BeanPropertyRowMapper<>(Review.class), id)
                .stream()
                .findAny()
                .orElse(null);
    }
}
