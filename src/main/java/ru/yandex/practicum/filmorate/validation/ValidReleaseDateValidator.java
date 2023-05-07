package ru.yandex.practicum.filmorate.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ValidReleaseDateValidator implements ConstraintValidator<ValidReleaseDate, LocalDate> {

    private String message;

    @Override
    public void initialize(final ValidReleaseDate constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final LocalDate releaseDate, final ConstraintValidatorContext context) {

        LocalDate date = LocalDate.of(1895, 12, 25);

        boolean isValid = releaseDate == null || releaseDate.isAfter(date);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        }

        return isValid;
    }
}
