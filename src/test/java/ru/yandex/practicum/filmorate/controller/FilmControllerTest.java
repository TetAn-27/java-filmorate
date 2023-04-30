package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.*;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();
    private Film film;

    @BeforeEach
    public void beforeEach() {
        film = new Film();
    }

    @Test
    public void testNameWhenNameIsEmpty() {
        film.setName("");

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "Валидация для параметра name некорректна");
    }

    @Test
    public void testNameWhenNameIsNull() {
        film.setName(null);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "Валидация для параметра name некорректна");
    }

    @Test
    public void testDurationWhenValuesNegative() {
        film.setName("Avatar");
        film.setDuration(-1);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "Валидация для параметра duration некорректна");
    }

    @Test
    public void testDescriptionWhenLengthIsMore200() {
        film.setName("Avatar");
        film.setDescription("DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription" +
                "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription" +
                "DescriptionDescription");

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "Валидация для параметра description некорректна");
    }

    @Test
    public void testReleaseDate() {
        film.setName("Avatar");
        film.setReleaseDate(LocalDate.MIN);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "Валидация для параметра description некорректна");
    }
}