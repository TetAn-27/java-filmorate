package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();
    private int filmId = 1;

    @GetMapping()
    public ArrayList<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @PostMapping()
    public Film postFilm(@Valid @RequestBody Film film) {
        log.debug("Фильм {} - добавлен", film.getName());
        assignId(film);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping()
    public Film putFilm(@Valid @RequestBody Film film, HttpServletResponse response) {
        if (films.containsKey(film.getId())) {
            log.debug("Пользователь с именем {} обновлен", film.getName());
            films.put(film.getId(), film);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return film;
    }

    public void assignId(Film film) {
        if (film.getId() == 0) {
            film.setId(filmId);
            filmId++;
        }
    }
}

