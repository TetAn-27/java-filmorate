package ru.yandex.practicum.filmorate.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ValidReleaseDateValidator.class)
@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ValidReleaseDate {

    String message() default "Параметр releaseDate не может быть раньше 28.12.1895";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
