package ru.yandex.practicum.filmorate.storage.validation;

import ru.yandex.practicum.filmorate.exceprions.ValidationException;

public interface Valid<T> {
    void validations(T t) throws ValidationException;
}
