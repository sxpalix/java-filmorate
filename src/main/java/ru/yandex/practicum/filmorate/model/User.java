package ru.yandex.practicum.filmorate.model;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class User {
    private int id;
    @NotBlank(message = "Email shouldn't be empty")
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Login shouldn't be empty")
    @Pattern(regexp = "[a-zA-Z0-9]{1,20}", message = "There must be no spaces")
    private String login;
    private String name;
    @PastOrPresent(message = "Birthday should be earlier")
    private LocalDate birthday;

    @Builder(toBuilder = true)
    public User(int id, String name, String email, String login, LocalDate birthday) {
        this.id = id;
        setName(name == null || name.isBlank() ? login : name);
        this.email = email;
        this.login = login;
        this.birthday = birthday;
    }
}
