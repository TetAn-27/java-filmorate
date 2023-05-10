package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;


public interface FilmStorage {

    public List<Film> getAllFilms();


    public Film postFilm(@Valid @RequestBody Film film);


    public Film putFilm(@Valid @RequestBody Film film, HttpServletResponse response);
}
