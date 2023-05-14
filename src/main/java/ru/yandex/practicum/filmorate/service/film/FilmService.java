package ru.yandex.practicum.filmorate.service.film;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;


    public void addLike(Integer id, Integer userId) {
        Set<Integer> likeList = getFilmById(id).getLikeList();
        likeList.add((userId));
        getFilmById(id).setLikeList(likeList);
    }

    public void deleteLike(Integer id, Integer userId) {
        Set<Integer> likeList = getFilmById(id).getLikeList();
        likeList.remove((userId));
        getFilmById(id).setLikeList(likeList);
    }

    public Film getFilmById(Integer id) {
        return filmStorage.getFilmById(id);
    }

    public List<Film> getTop10Films(Integer count) {
        return filmStorage.getAllFilms().stream()
                .sorted((f, f1) -> compare(f, f1))
                .limit(count)
                .collect(Collectors.toList());
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film postFilm(Film film) {
        return filmStorage.postFilm(film);
    }

    public Film putFilm(Film film, HttpServletResponse response) {
        return filmStorage.putFilm(film, response);
    }

    private int compare(Film f, Film f1) {
        return f.getLikeList().size() - f1.getLikeList().size();
    }
}
