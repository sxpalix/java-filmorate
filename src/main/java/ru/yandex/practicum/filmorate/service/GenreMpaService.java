package ru.yandex.practicum.filmorate.service;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import java.util.List;

public interface GenreMpaService<T> {
    List<T> getAll();

    T get(int id) throws IncorrectValuesException;
}
