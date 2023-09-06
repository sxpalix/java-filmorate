package ru.yandex.practicum.filmorate.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.exceprions.IncorrectValuesException;
import ru.yandex.practicum.filmorate.exceprions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.filmLike.FilmLikeService;
import ru.yandex.practicum.filmorate.storage.db.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DbFilmLikeServiceTest {
    private final UserDbStorage userDbStorage;
    private final FilmDbStorage filmDbStorage;
    private final FilmLikeService filmLikeService;
    private static User user;
    private static Film film;
    private static final Mpa mpa = new Mpa(5, "NC-17");

    @BeforeAll
    public static void beforeAll() {
        user = createUserForTest(1);
        film = createFilmForTest();
    }

    @BeforeEach
    public void beforeEach() throws ValidationException, IncorrectValuesException {
        userDbStorage.post(user);
        filmDbStorage.post(film);
        filmLikeService.likeFilm(1, 1);
    }

    @Test
    public void shouldReturnMostPopular() {
        List<Film> films = filmLikeService.mostPopularFilm(1,0, 1894);
        assertEquals(film, films.get(0));
    }

    @Test
    public void shouldReturnMostPopularByGenreId() {
        List<Film> films = filmLikeService.mostPopularFilm(1,1, 1894);
        assertEquals(film, films.get(0));
    }

    @Test
    public void shouldReturnMostPopularByYear() {
        List<Film> films = filmLikeService.mostPopularFilm(1,0, 2000);
        assertEquals(film, films.get(0));
    }

    @Test
    public void shouldReturnMostPopularByGenreIdAndYear() {
        List<Film> films = filmLikeService.mostPopularFilm(1,1, 2000);
        assertEquals(film, films.get(0));
    }

    @Test
    public void shouldNotReturnMostPopularWithNoExistGenreId() {
        List<Film> films = filmLikeService.mostPopularFilm(1,2, 1894);
        assertEquals(0, films.size());
    }

    @Test
    public void shouldNotReturnMostPopularWithNoExistYear() {
        List<Film> films = filmLikeService.mostPopularFilm(1,0, 1999);
        assertEquals(0, films.size());
    }

    @Test
    public void shouldNotReturnMostPopularWithNoExistGenreIdAndYear() {
        List<Film> films = filmLikeService.mostPopularFilm(1,2, 1999);
        assertEquals(0, films.size());
    }

    private static User createUserForTest(int id) {
        return new User(
                id,
                "user" + id + "@gmail.kz",
                "user" + id + "Login",
                "user" + id + "Name",
                LocalDate.parse("2000-01-0" + id)
        );
    }

    private static Film createFilmForTest() {
        Film film = new Film();
        film.setId(1);
        film.setName("film for review");
        film.setDescription("film for test");
        film.setReleaseDate(LocalDate.parse("2000-01-01"));
        film.setDuration(250);
        film.setMpa(mpa);
        film.getGenres().add(new Genre(1, "Комедия"));

        return film;
    }
}
