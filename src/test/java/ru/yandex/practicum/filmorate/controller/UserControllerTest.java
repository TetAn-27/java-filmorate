package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();
    private User user;

    @BeforeEach
    public void beforeEach() {
        user = new User();
    }

    @Test
    public void testEmailWhenEmailIsEmpty() {
        user.setEmail("");
        user.setLogin("TetAn");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Валидация для параметра email некорректна");
    }

    @Test
    public void testEmail() {
        user.setEmail("yandex.ru");
        user.setLogin("TetAn");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Валидация для параметра email некорректна");
    }

    @Test
    public void testEmailWhenEmailNormal() {
        user.setEmail("YPracticumTop@yandex.ru");
        user.setLogin("TetAn");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size(), "Валидация для параметра email некорректна");
    }

    @Test
    public void testLoginWhenLoginIsEmpty() {
        user.setEmail("YPracticumTop@yandex.ru");
        user.setLogin("");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(2, violations.size(), "Валидация для параметра login некорректна");
    }

    @Test
    public void testLoginWhenSpacing() {
        user.setEmail("YPracticumTop@yandex.ru");
        user.setLogin("Tet An");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Валидация для параметра login некорректна");
    }

    @Test
    public void testBirthdayWhenNormal() {
        user.setEmail("YPracticumTop@yandex.ru");
        user.setLogin("TetAn");
        user.setBirthday(LocalDateTime.MAX);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Валидация для параметра login некорректна");
    }
}