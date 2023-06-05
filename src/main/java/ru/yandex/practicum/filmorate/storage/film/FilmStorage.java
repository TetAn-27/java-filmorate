package ru.yandex.practicum.filmorate.storage.film;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.yandex.practicum.filmorate.model.Film;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Qualifier("filmDbStorage")
public interface FilmStorage {

    Map<Integer, Film> films = new HashMap<>();

    List<Film> getAllFilms();

    Film postFilm(Film film);

    Film putFilm(Film film, HttpServletResponse response);

    Film getFilmById(Integer id);
}
