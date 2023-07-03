package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Film {
    private int id;
    @NotEmpty(message = "Name shouldn't be empty")
    private final String name;
    @Size(max = 200, message = "Descriptions should be less then 200")
    private final String description;
    private final LocalDate releaseDate; // Не понял как ограничить дату релиза в соответствии с тз
    @Min(value = 0, message = "Duration should be more then 0")
    private final int duration;
    private int rate;
}
