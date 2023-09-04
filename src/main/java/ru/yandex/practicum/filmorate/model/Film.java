package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Film {
    private int id;
    @NotBlank(message = "Name shouldn't be empty")
    private String name;
    @Size(max = 200, message = "Descriptions should be less then 200")
    @NotBlank
    private String description;
    private LocalDate releaseDate;
    @Min(value = 0, message = "Duration should be more then 0")
    private int duration;
    private double rating;
    @NotNull
    private Mpa mpa;
    private Set<Genre> genres = new LinkedHashSet<>();
    private Set<Director> directors = new LinkedHashSet<>();

    @Builder(toBuilder = true)
    public Film(int id, String name, String description, LocalDate releaseDate, int duration, double rating,
                Set<Genre> genres, Mpa mpa, Set<Director> directors) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rating = rating;
        setDirectors(directors == null ? new HashSet<>() : directors);
        setGenres(genres == null ? new HashSet<>() : genres);
        this.mpa = mpa;
    }
}
