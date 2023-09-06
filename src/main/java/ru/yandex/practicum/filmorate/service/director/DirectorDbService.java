package ru.yandex.practicum.filmorate.service.director;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DirectorDbService implements DirectorService {
    private final DirectorDbStorage storage;

    @Override
    public Director put(Director director) throws ValidationException, IncorrectValuesException {
        return storage.put(director);
    }

    @Override
    public List<Director> getAll() {
        return storage.getAll();
    }

    @Override
    public Director post(Director director) throws ValidationException, IncorrectValuesException {
        return storage.post(director);
    }

    @Override
    public Director get(int id) throws IncorrectValuesException {
        return storage.get(id);
    }

    @Override
    public void delete(int id) {
        storage.delete(id);
    }
}
