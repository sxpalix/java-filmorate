package ru.yandex.practicum.filmorate.service.memory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Qualifier
public class InMemoryFilmService implements FilmService {
    private final Storage<Film> storage;
    private final UserService userService;

    private InMemoryFilmService(@Qualifier("FilmDbStorage") Storage<Film> storage,
                                @Qualifier("DbUserService") UserService userService) {
        this.storage = storage;
        this.userService = userService;
    }

    private void existenceCheckFilm(int id) throws IncorrectValuesException {
        if (storage.get(id) == null) {
            log.error("Film with {} isn't added.", id);
            throw new IncorrectValuesException(String.format("Film with %s isn't added.", id));
        }
    }

    private void existenceCheckUser(int id) throws IncorrectValuesException {
        if (userService.getUserById(id) == null) {
            log.error("User with {} id doesn't exist", id);
            throw new IncorrectValuesException(String.format("User with %s id doesn't exist.", id));
        }
    }

    private Set<Integer> filmsLikes(int id) {
        try {
            existenceCheckFilm(id);
            return storage.get(id).getUsersLikes();
        } catch (IncorrectValuesException exception) {
            log.error("User with {} id doesn't exist", id);
            return new HashSet<>();
        }
    }

    @Override
    public void likeTheMovie(int filmId, int userId) throws IncorrectValuesException {
        existenceCheckFilm(filmId);
        existenceCheckUser(userId);
        filmsLikes(filmId).add(userId);
    }

    @Override
    public void unlikeTheMovie(int filmId, int userId) throws IncorrectValuesException {
        existenceCheckFilm(filmId);
        existenceCheckUser(userId);
        filmsLikes(filmId).remove(userId);
    }

    @Override
    public List<Film> mostPopularFilm(Integer count) {
        int limit = Optional.ofNullable(count).orElse(10);
        return storage.getAll().stream()
                .sorted((o1, o2) -> {
                    if (o1.getUsersLikes().size() > o2.getUsersLikes().size()) {
                        return -1;
                    } else if (o1.getUsersLikes().size() == o2.getUsersLikes().size()) {
                        return 0;
                    } else return 1;
                })
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public Film putFilm(Film film) throws ValidationException, IncorrectValuesException {
        return storage.put(film);
    }

    @Override
    public List<Film> getFilms() {
        return storage.getAll();
    }

    @Override
    public Film postFilm(Film film) throws ValidationException, IncorrectValuesException {
        return storage.post(film);
    }

    @Override
    public Film getFilm(int id) throws IncorrectValuesException {
        return storage.get(id);
    }
}
