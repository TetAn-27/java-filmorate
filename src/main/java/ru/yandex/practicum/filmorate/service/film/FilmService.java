package ru.yandex.practicum.filmorate.service.film;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class FilmService {

    @Qualifier("filmDbStorage") @NonNull private final FilmStorage filmStorage;

    public void addLike(Integer id, Integer userId) {
        try {
            log.debug("Пользователь с id {} поставил лайк фильму {}", userId, id);
            filmStorage.addLike(id, userId);
        } catch (DataAccessException ex) {
            log.error("User с ID {} не был найден для обновления", userId);
            throw new NotFoundException("User с таким ID не был найден");
        }
    }

    public void deleteLike(Integer id, Integer userId) {
        if (filmStorage. getUserIdsWhoLikedFilm(id).contains(userId)) {
            filmStorage.deleteLike(id, userId);
        } else {
            log.error("Film с ID {} не был найден для обновления", id);
            throw new NotFoundException("Film с таким ID не был найден");
        }
    }

    public Film getFilmById(Integer id) {
        try {
            return filmStorage.getFilmById(id);
        } catch (DataAccessException ex) {
            log.error("Film с ID {} не был найден для обновления", id);
            throw new NotFoundException("Film с таким ID не был найден");
        }
    }

    public List<Film> getTop10Films(Integer count) {
        return filmStorage.getAllFilms().stream()
                .sorted(this::compare)
                .limit(count)
                .collect(Collectors.toList());
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Optional<Film> postFilm(Film film) {
        return filmStorage.postFilm(film);
    }

    public Optional<Film> putFilm(Film film) {
        try {
            return filmStorage.putFilm(film);
        } catch (DataAccessException ex) {
            log.error("Film с ID {} не был найден для обновления", film.getId());
            throw new NotFoundException("Film с таким ID не был найден");
        }
    }

    private int compare(Film f, Film f1) {
        return f1.getLikeList().size() - f.getLikeList().size();
    }
}
