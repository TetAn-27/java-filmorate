package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

public interface UserStorage {
   HashMap<Integer, User> users = new HashMap<>();

    List<User> getAllUsers();

    User postUser(User user);

    User putUser(User user, HttpServletResponse response);

    User getUserById(Integer id);
}
