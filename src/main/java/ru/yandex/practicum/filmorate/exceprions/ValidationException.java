package ru.yandex.practicum.filmorate.exceprions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationException extends Exception {
    public ValidationException(String exception) {
        super(exception);
    }
}
