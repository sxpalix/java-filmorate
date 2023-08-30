package ru.yandex.practicum.filmorate.service.film.db;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.storage.Storage;
import java.util.List;

@Slf4j
@Service("DbFilmService")
public class DbFilmService implements FilmService {
    private final Storage<Film> storage;

    @Autowired
    public DbFilmService(@Qualifier("FilmDbStorage")Storage<Film> storage) {
        this.storage = storage;
    }

    @Override
    public Film put(Film film) throws ValidationException, IncorrectValuesException {
        return storage.put(film);
    }

    @Override
    public List<Film> getAll() {
        return storage.getAll();
    }

    @Override
    public Film post(Film film) throws ValidationException, IncorrectValuesException {
        return storage.post(film);
    }

    @Override
    public Film get(int id) throws IncorrectValuesException {
        return storage.get(id);
    }

    @Override
    public void delete(Film film) throws IncorrectValuesException {
        storage.delete(film);
    }
}
