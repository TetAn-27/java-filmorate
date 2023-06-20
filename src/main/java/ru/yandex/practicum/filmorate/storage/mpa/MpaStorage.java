package ru.yandex.practicum.filmorate.storage.mpa;

import java.util.List;
import java.util.Optional;
import ru.yandex.practicum.filmorate.model.Mpa;

public interface MpaStorage {
    Optional<Mpa> getMpaById(int id);

    List<Mpa> getAllMpa();
}
