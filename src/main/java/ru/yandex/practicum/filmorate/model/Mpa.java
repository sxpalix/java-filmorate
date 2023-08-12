package ru.yandex.practicum.filmorate.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.validation.constraints.NotBlank;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mpa {
    @NotBlank
    private int id;
    private String name;
}
