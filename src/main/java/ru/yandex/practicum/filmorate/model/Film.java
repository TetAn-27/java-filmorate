package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.validation.ValidReleaseDate;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class Film {

    private int id;
    @NotEmpty
    private String name;
    @Size(max = 200)
    private String description;
    @ValidReleaseDate
    private LocalDate releaseDate;
    @Positive
    private Integer duration;
}
