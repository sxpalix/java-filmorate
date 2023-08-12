package ru.yandex.practicum.filmorate.service.dbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.GenreMpaService;
import ru.yandex.practicum.filmorate.storage.db.MpaDbStorage;
import java.util.List;

@Service("DbMpaService")
public class DbMpaService implements GenreMpaService<Mpa> {
    private final MpaDbStorage storage;

    @Autowired
    public DbMpaService(MpaDbStorage storage) {
        this.storage = storage;
    }

    public List<Mpa> getAll() {
        return storage.getAll();
    }

    public Mpa get(int id) throws IncorrectValuesException {
        return storage.get(id);
    }
}
