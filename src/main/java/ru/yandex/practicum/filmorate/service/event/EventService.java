package ru.yandex.practicum.filmorate.service.event;


import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Event;

import java.util.List;

public interface EventService {
    List<Event> getFeed(int id) throws IncorrectValuesException, ValidationException;

    void add(Event event);
}

