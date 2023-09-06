package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.enums.Operation;
import ru.yandex.practicum.filmorate.model.enums.EventType;

import javax.validation.constraints.NotNull;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Event {
    private int eventId;
    @NotNull
    private long timestamp;
    @NotNull
    private EventType eventType;
    @NotNull
    private Operation operation;
    @NotNull
    private int userId;
    @NotNull
    private int entityId;
}