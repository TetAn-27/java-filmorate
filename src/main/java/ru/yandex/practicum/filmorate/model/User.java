package ru.yandex.practicum.filmorate.model;

import java.time.LocalDateTime;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;

@Data
public class User {

    private int id;
    @Email
    @NotEmpty
    private String email;
    @NotBlank
    private String login;
    private String name;
    @Past
    private LocalDateTime birthday;
}
