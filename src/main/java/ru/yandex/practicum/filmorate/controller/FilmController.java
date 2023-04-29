package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping()
    public Map<Integer, Film> getAllFilms() {
        return films;
    }

    @PostMapping()
    public Film postFilm(@Valid @RequestBody Film film) {
        log.debug("Фильм {} - добавлен", film.getName());
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping()
    public Film putFilm(@Valid @RequestBody Film film) {
        for (Film value : films.values()) {
            if (value.equals(film)) {
                films.remove(film.getId());
                log.debug("Фильм {} - обновлен", film.getName());
            } else {
                log.debug("Фильм {} - добавлен", film.getName());
            }
            films.put(film.getId(), film);
            return film;
        }
        return film;
    }
}

