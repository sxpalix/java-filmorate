package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private int id;
    @NotEmpty(message = "Email shouldn't be empty")
    @Email(message = "Email should be valid")
    private final String email;
    @NotEmpty(message = "Login shouldn't be empty") //Не разобрался как исключить пробелы
    private final String login;
    private String name;
    @PastOrPresent(message = "Birthday should be earlier")
    private final LocalDate birthday;
}
