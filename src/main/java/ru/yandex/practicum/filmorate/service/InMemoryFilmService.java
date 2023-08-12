package ru.yandex.practicum.filmorate.service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InMemoryFilmService implements FilmService {
    private final FilmStorage storage;
    private final UserService userService;

    private InMemoryFilmService(FilmStorage storage, UserService userService) {
        this.storage = storage;
        this.userService = userService;
    }

    private void existenceCheckFilm(int id) throws IncorrectValuesException {
        if (storage.getFilm(id) == null) {
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
            return storage.getFilm(id).getUsersLikes();
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
        return storage.getFilms().stream()
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
        return storage.putFilm(film);
    }

    @Override
    public List<Film> getFilms() {
        return storage.getFilms();
    }

    @Override
    public Film postFilm(Film film) throws ValidationException {
        return storage.postFilm(film);
    }

    @Override
    public Film getFilm(int id) throws IncorrectValuesException {
        return storage.getFilm(id);
    }
}