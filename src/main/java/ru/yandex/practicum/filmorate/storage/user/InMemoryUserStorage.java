package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int userId = 1;

    @GetMapping()
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping()
    public User postUser(User user) {
        log.debug("Пользователь с именем {} создан", user.getName());
        return createUser(user);
    }

    @PutMapping()
    public User putUser(User user, HttpServletResponse response) {
        if (users.containsKey(user.getId())) {
            validatorName(user);
            log.debug("Пользователь с именем {} обновлен", user.getName());
            users.put(user.getId(), user);
        } else {
            throw new NotFoundException("User с таким ID не был найден");
        }
        return user;
    }

    public User createUser(User user) {
        validatorName(user);
        if (user.getId() == 0) {
            user.setId(userId);
            userId++;
        }
        users.put(user.getId(), user);
        return user;
    }

    public void validatorName(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
            log.debug("Значение name пустое. В качестве имени будет использоваться login");
        }
    }

    public User getUserById(Integer id) {
        if (users.containsKey(id)) {
            log.debug("Пользователь с id: {}", id);
            return users.get(id);
        } else {
            throw new NotFoundException("User с таким ID не был найден");
        }
    }
}
