package ru.yandex.practicum.filmorate.controllers.other;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping()
    public List<Review> getAll(@RequestParam(required = false) Integer filmId,
                                       @RequestParam(required = false) Integer count) {
        return reviewService.getAll(filmId, count);
    }

    @GetMapping("/{id}")
    public Review getById(@PathVariable int id) throws IncorrectValuesException {
        return reviewService.get(id);
    }

    @PostMapping()
    public Review add(@Valid @RequestBody Review review) throws ValidationException, IncorrectValuesException {
        return reviewService.post(review);
    }

    @PutMapping()
    public Review update(@Valid @RequestBody Review review) throws ValidationException, IncorrectValuesException {
        return reviewService.put(review);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) throws IncorrectValuesException {
        reviewService.delete(reviewService.get(id));
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id,
                        @PathVariable int userId) throws IncorrectValuesException {
        reviewService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable int id,
                           @PathVariable int userId) throws IncorrectValuesException {
        reviewService.deleteLike(id, userId);
    }

    @PutMapping("/{id}/dislike/{userId}")
    public void addDislike(@PathVariable int id,
                           @PathVariable int userId) throws IncorrectValuesException {
        reviewService.addDislike(id, userId);
    }

    @DeleteMapping("/{id}/dislike/{userId}")
    public void removeDislike(@PathVariable int id,
                              @PathVariable int userId) throws IncorrectValuesException {
        reviewService.deleteDislike(id, userId);
    }
}
