package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class User {

    private int id;

    private String name;

    @NotBlank
    @Pattern(regexp = "\\S+")
    private String login;

    @Email
    @NotEmpty
    private String email;

    @Past
    private LocalDate birthday;

    public User(int id, String name, String login, String email, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.email = email;
        this.birthday = birthday;
    }
//private Set<Integer> friends = new HashSet<>();
}
