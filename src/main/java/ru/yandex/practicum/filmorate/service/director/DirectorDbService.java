package ru.yandex.practicum.filmorate.service.director;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.storage.db.DirectorDbStorage;

import java.util.List;

@Service
@Slf4j
public class DirectorDbService implements DirectorService {
    private final DirectorDbStorage storage;

    @Autowired
    public DirectorDbService(DirectorDbStorage storage) {
        this.storage = storage;
    }

    @Override
    public Director put(Director director) throws ValidationException, IncorrectValuesException {
        log.info("Update director");
        return storage.put(director);
    }

    @Override
    public List<Director> getAll() {
        log.info("Return list with all directors");
        return storage.getAll();
    }

    @Override
    public Director post(Director director) throws ValidationException, IncorrectValuesException {
        log.info("Create new director");
        return storage.post(director);
    }

    @Override
    public Director get(int id) throws IncorrectValuesException {
        log.info("GET director by id");
        return storage.get(id);
    }

    @Override
    public void delete(int id) {
        log.info("DELETE director by id");
        storage.delete(id);
    }
}
