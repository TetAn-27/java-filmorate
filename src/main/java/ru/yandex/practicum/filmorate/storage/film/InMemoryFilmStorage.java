package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int filmId = 1;

    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    public Film postFilm(Film film) {
        assignId(film);
        films.put(film.getId(), film);
        log.debug("Фильм {} - добавлен", film.getName());
        return film;
    }

    public Film putFilm(Film film, HttpServletResponse response) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.debug("Пользователь с именем {} обновлен", film.getName());
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return film;
    }

    public Film getFilmById(Integer id) {
        log.debug("Фильм с id: {}", id);
        return films.get(id);
    }

    public void assignId(Film film) {
        if (film.getId() == 0) {
            film.setId(filmId);
            filmId++;
        }
    }
}
