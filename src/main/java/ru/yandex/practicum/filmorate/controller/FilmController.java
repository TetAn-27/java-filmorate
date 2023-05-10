package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

   InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
    @GetMapping()
    public List<Film> getAllFilms() {
        return inMemoryFilmStorage.getAllFilms();
    }

    @PostMapping()
    public Film postFilm(@Valid @RequestBody Film film) {
        return inMemoryFilmStorage.postFilm(film);
    }

    @PutMapping()
    public Film putFilm(@Valid @RequestBody Film film, HttpServletResponse response) {
        return inMemoryFilmStorage.putFilm(film, response);
    }
}

