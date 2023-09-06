package ru.yandex.practicum.filmorate.service.mpaGenre.db;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.mpaGenre.GenreMpaService;
import ru.yandex.practicum.filmorate.storage.db.GenreDbStorage;
import java.util.List;

@Service("DbGenreService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DbGenreService implements GenreMpaService<Genre> {
    private final GenreDbStorage storage;

    public List<Genre> getAll() {
        return storage.getAll();
    }

    public Genre get(int id) throws IncorrectValuesException {
        return storage.get(id);
    }
}
