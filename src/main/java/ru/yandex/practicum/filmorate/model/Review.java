package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Review {
    private int reviewId;

    @NotBlank(message = "Content shouldn't be empty")
    private String content;

    @NotNull
    private Boolean isPositive;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer filmId;

    private int useful;
    private List<Integer> likes;
    private List<Integer> dislikes;

    public Review(int id, String content, Boolean isPositive, Integer userId, Integer filmId) {
        this.reviewId = id;
        this.content = content;
        this.isPositive = isPositive;
        this.userId = userId;
        this.filmId = filmId;
    }
}
