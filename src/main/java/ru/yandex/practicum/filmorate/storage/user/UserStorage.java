package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.User;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

public interface UserStorage {
    public List<User> getAllUsers();

    public User postUser(@Valid @RequestBody User user);

    public User putUser(@Valid @RequestBody User user, HttpServletResponse response);
}
