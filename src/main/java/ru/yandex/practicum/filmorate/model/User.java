package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import lombok.Builder;
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
    private LocalDate birthday;

    private Set<Integer> friends = new HashSet<>();
}
