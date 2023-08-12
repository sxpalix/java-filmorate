package ru.yandex.practicum.filmorate.storage.validation;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

@Component("UserValid")
public class UserValid implements Valid<User> {

    @Override
    public void validations(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
