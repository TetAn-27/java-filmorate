package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final HashMap<Integer, User> users = new HashMap<>();
    private int userId = 1;

    @GetMapping()
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping()
    public User postUser(@Valid @RequestBody User user) {
        log.debug("Пользователь с именем {} создан", user.getName());
        return createUser(user);
    }

    @PutMapping()
    public User putUser(@Valid @RequestBody User user, HttpServletResponse response) {
        if (users.containsKey(user.getId())) {
            validatorName(user);
            log.debug("Пользователь с именем {} обновлен", user.getName());
            users.put(user.getId(), user);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return user;
    }

    public User createUser(@RequestBody User user) {
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
}
