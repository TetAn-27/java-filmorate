package ru.yandex.practicum.filmorate.storage.film;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FilmStorage {

    Map<Integer, Film> films = new HashMap<>();

    List<Film> getAllFilms();

    Optional<Film> postFilm(Film film);

    Optional<Film> putFilm(Film film);

    Film getFilmById(Integer id);

    void addLike(Integer id, Integer userId);

    List<Integer>  getUserIdsWhoLikedFilm(Integer id);

    void deleteLike(Integer id, Integer userId);
}
