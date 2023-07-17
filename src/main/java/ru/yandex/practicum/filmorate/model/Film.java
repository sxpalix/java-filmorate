package ru.yandex.practicum.filmorate.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    private int id;
    @NotBlank(message = "Name shouldn't be empty")
    private String name;
    @Size(max = 200, message = "Descriptions should be less then 200")
    private String description;
    private LocalDate releaseDate;
    @Min(value = 0, message = "Duration should be more then 0")
    private int duration;
    private int rate;
}
