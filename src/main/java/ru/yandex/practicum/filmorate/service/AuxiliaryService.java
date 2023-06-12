package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AuxiliaryService {
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;

    public List<Genre> getAllGenres() {
        return genreStorage.getAllGenres();
    }

    public Optional<Genre> getGenreById(Integer id) {
        if (genreStorage.getGenreById(id).isEmpty()) {
            throw new NotFoundException("Жанр с таким ID не был найден");
        }
        log.debug("Жанр с id: {}", id);
        return genreStorage.getGenreById(id);
    }

    public List<Mpa> getAllMpa() {
        return mpaStorage.getAllMpa();
    }

    public Optional<Mpa> getMpaById(Integer id) {
        if (mpaStorage.getMpaById(id).isEmpty()) {
            throw new NotFoundException("Рейтинг с таким ID не был найден");
        }
            log.debug("Рейтинг с id: {}", id);
            return mpaStorage.getMpaById(id);
    }
}
