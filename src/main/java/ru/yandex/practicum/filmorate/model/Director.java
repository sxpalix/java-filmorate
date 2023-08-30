package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Director {
    private int id;
    @NotBlank
    @NotEmpty
    private String name;
}
