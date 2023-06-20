package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int filmId = 1;

    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    public Optional<Film> postFilm(Film film) {
        assignId(film);
        films.put(film.getId(), film);
        log.debug("Фильм {} - добавлен", film.getName());
        return Optional.of(film);
    }

    @Override
    public Optional<Film> putFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.debug("Фильм с именем {} обновлен", film.getName());
            return Optional.of(film);
        } else {
            throw new NotFoundException("Film с таким ID не был найден");
        }
    }

    public Film getFilmById(Integer id) {
        if (films.containsKey(id)) {
            log.debug("Фильм с id: {}", id);
            return films.get(id);
        } else {
            throw new NotFoundException("Film с таким ID не был найден");
        }
    }

    @Override
    public void addLike(Integer id, Integer userId) {
        films.get(id).getLikeList().add(userId);
    }

    @Override
    public List<Integer> getUserIdsWhoLikedFilm(Integer id) {
        return films.get(id).getLikeList();
    }

    @Override
    public void deleteLike(Integer id, Integer userId) {
        films.get(id).getLikeList().remove(userId);
    }

    public void assignId(Film film) {
        if (film.getId() == 0) {
            film.setId(filmId);
            filmId++;
        }
    }
}
