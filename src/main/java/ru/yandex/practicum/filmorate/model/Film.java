package ru.yandex.practicum.filmorate.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

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
}
