package ru.yandex.practicum.filmorate.controllers.other;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.review.ReviewService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reviews")
@AllArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Review> getAll(@RequestParam(required = false) Integer filmId,
                               @RequestParam(required = false) Integer count) {
        log.info("GET all reviews");
        return reviewService.getAll(filmId, count);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Review getById(@PathVariable int id) throws IncorrectValuesException {
        log.info("GET review by id");
        return reviewService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Review post(@Valid @RequestBody Review review) throws ValidationException, IncorrectValuesException {
        log.info("POST new review");
        return reviewService.post(review);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Review put(@Valid @RequestBody Review review) throws ValidationException, IncorrectValuesException {
        log.info("PUT updated review");
        return reviewService.put(review);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteById(@PathVariable int id) throws IncorrectValuesException {
        log.info("DELETE review by id");
        reviewService.deleteById(reviewService.getById(id));
        return "Review successfully deleted";
    }

    @PutMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void addLike(@PathVariable int id,
                        @PathVariable int userId) throws IncorrectValuesException {
        log.info("PUT like for review");
        reviewService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteLike(@PathVariable int id,
                             @PathVariable int userId) throws IncorrectValuesException {
        log.info("DELETE like from review");
        reviewService.deleteLike(id, userId);
        return "Like successfully deleted from review";
    }

    @PutMapping("/{id}/dislike/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void addDislike(@PathVariable int id,
                           @PathVariable int userId) throws IncorrectValuesException {
        log.info("PUT dislike for review");
        reviewService.addDislike(id, userId);
    }

    @DeleteMapping("/{id}/dislike/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteDislike(@PathVariable int id,
                                @PathVariable int userId) throws IncorrectValuesException {
        log.info("DELETE dislike from review");
        reviewService.deleteDislike(id, userId);
        return "Dislike successfully deleted from review";
    }
}
