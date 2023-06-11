package ru.yandex.practicum.filmorate.storage.film;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Qualifier("filmDbStorage")
public interface FilmStorage {

    Map<Integer, Film> films = new HashMap<>();

    List<Film> getAllFilms();

    Optional<Film> postFilm(Film film);

    Optional<Film> putFilm(Film film);

    Film getFilmById(Integer id);

    void addLike(Integer id, Integer userId);

    List<Integer> getLikeList(Integer id);

    void deleteLike(Integer id, Integer userId);
}
