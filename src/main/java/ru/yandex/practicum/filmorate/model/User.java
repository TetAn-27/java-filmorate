package ru.yandex.practicum.filmorate.model;

import java.time.LocalDateTime;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class User {

    private int id;
    @Email
    @NotEmpty
    private String email;
    @NotBlank
    @Pattern(regexp = "\\S+")
    private String login;
    private String name;
    @Past
    private LocalDateTime birthday;
}
