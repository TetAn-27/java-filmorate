package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.AuxiliaryService;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class AuxiliaryController {
    private final AuxiliaryService auxiliaryService;

    @GetMapping("/genres")
    public List<Genre> getAllGenres() {
        return auxiliaryService.getAllGenres();
    }

    @GetMapping("/genres/{id}")
    public Optional<Genre> getGenreById(@PathVariable("id") Integer id) {
        return auxiliaryService.getGenreById(id);
    }

    @GetMapping("/mpa")
    public List<Mpa> getAllMpa() {
        return auxiliaryService.getAllMpa();
    }

    @GetMapping("/mpa/{id}")
    public Optional<Mpa> getMpaById(@PathVariable("id") Integer id) {
        return auxiliaryService.getMpaById(id);
    }
}
