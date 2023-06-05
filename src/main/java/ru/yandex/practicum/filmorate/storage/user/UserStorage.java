package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Qualifier;
import ru.yandex.practicum.filmorate.model.User;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Qualifier("userDbStorage")
public interface UserStorage {
   HashMap<Integer, User> users = new HashMap<>();

    List<User> getAllUsers();

    void postUser(User user);

    void putUser(User user);

    User getUserById(Integer id);
}
