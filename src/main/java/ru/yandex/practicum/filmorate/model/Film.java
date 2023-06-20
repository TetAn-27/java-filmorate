package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.ValidReleaseDate;

import javax.validation.constraints.*;

import java.time.LocalDate;
import java.util.*;

@Data
@AllArgsConstructor
@Builder
public class Film {
    public Film() {
    }

    private int id;
    @NotEmpty
    private String name;
    @Size(max = 200)
    private String description;
    @ValidReleaseDate
    private LocalDate releaseDate;
    @Positive
    private Integer duration;
    private Mpa mpa;
    private List<Genre> genres;
    private List<Integer> likeList;

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("name", name);
        values.put("description", description);
        values.put("release_date", releaseDate);
        values.put("duration", duration);
        values.put("mpa_id", mpa.getId());

        return values;
    }
}
