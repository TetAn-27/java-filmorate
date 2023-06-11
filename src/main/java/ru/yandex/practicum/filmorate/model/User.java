package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@Builder
public class User {
    public User() {

    }

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

    private List<Integer> friends;

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("name", name);
        values.put("login", login);
        values.put("email", email);
        values.put("birthday", birthday);

        return values;
    }
}
