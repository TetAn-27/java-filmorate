package ru.yandex.practicum.filmorate.DAO;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDbStorageTest {

    private final FilmDbStorage filmStorage;
    private Film film;

    @BeforeEach
    public void beforeEach() {
        List<Integer> likeList = new ArrayList<>();
        LocalDate releaseDate = LocalDate.of(2000,1,27);
        Mpa mpa = new Mpa(1, "G");
        Genre genre = new Genre(1, "Комедия");
        List<Genre> genres = new ArrayList<>();
        genres.add(genre);
        film = new Film(0, "name", "description", releaseDate, 50, mpa, genres, likeList);
        filmStorage.postFilm(film);
    }

    @Test
    void testGetAllFilms() {
        List<Film> films = new ArrayList<>();
        films.add(film);
        List<Film> filmsActual = filmStorage.getAllFilms();
        assertEquals(films, filmsActual);
    }

    @Test
    void testPostFilm() {
        Film filmActual = filmStorage.postFilm(film).get();
        assertEquals(film, filmActual);
    }

    @Test
    void testPutFilm() {
        film.setDescription("description1234");
        film.setDuration(150);
        Film filmActual = filmStorage.putFilm(film).get();
        assertEquals(film, filmActual);
    }

    @Test
    void testGetFilmById() {
        Film filmActual = filmStorage.getFilmById(3);
        assertEquals(film, filmActual);
    }
}