package ru.yandex.practicum.filmorate.validators;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class FilmValidator {
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 1;

    public List<Film> getFilms(){
        return new ArrayList<>(films.values());
    }

    public Film postFilm(Film film) throws ValidationException {
        if (film.getName().isEmpty()){
            throw new ValidationException("Название не может быть пустым.");
        }
        if (film.getDescription().length() > 201) {
            throw new ValidationException("Максимальная длина описания — 200 символов.");
        }
        if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года.");
        }
        if (film.getDuration() < 1) {
            throw new ValidationException("Продолжительность фильма должна быть положительной.");
        }
        film.setId(id);
        id ++;
        films.put(film.getId(), film);
        return film;
    }

    public Film putFilm(Film film) throws ValidationException {
        if (film.getName().isEmpty()){
            throw new ValidationException("Название не может быть пустым.");
        }
        if (film.getDescription().length() > 201) {
            throw new ValidationException("Максимальная длина описания — 200 символов.");
        }
        if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года.");
        }
        if (film.getDuration() < 1) {
            throw new ValidationException("Продолжительность фильма должна быть положительной.");
        }
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            throw new ValidationException("Обновление фильма с несуществующим id.");
        }
        return film;
    }
}