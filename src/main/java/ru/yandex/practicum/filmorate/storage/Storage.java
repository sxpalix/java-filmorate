package ru.yandex.practicum.filmorate.storage;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import java.util.List;

public interface Storage<T> {
    T put(T t) throws ValidationException, IncorrectValuesException;

    List<T> getAll();

    T post(T t) throws ValidationException, IncorrectValuesException;

    T get(int id) throws IncorrectValuesException;

    void delete (T t) throws IncorrectValuesException;
}
