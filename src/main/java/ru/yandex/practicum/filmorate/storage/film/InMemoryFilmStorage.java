package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int filmId = 1;

    @GetMapping()
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @PostMapping()
    public Film postFilm(@Valid @RequestBody Film film) {
        assignId(film);
        films.put(film.getId(), film);
        log.debug("Фильм {} - добавлен", film.getName());
        return film;
    }

    @PutMapping()
    public Film putFilm(@Valid @RequestBody Film film, HttpServletResponse response) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.debug("Пользователь с именем {} обновлен", film.getName());
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
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
