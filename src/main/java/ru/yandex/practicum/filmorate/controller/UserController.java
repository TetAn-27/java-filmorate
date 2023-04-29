package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping()
    public Map<Integer, User> getAllUsers() {
        return users;
    }

    @PostMapping()
    public User postUser(@Valid @RequestBody User user) {
        log.debug("Пользователь с именем {} создан", user.getName());
        return createUser(user);
    }

    @PutMapping()
    public User putUser(@Valid @RequestBody User user) {
        for (User value : users.values()) {
            if (value.equals(user)) {
                users.remove(user.getId());
                log.debug("Пользователь с именем {} обновлен", user.getName());
                return createUser(user);
            } else {
                log.debug("Пользователь с именем {} создан", user.getName());
                return createUser(user);
            }
        }
        return createUser(user);
    }

    public User createUser(@RequestBody User user) {
        validatorName (user);
        users.put(user.getId(), user);
        return user;
    }

    public void validatorName (User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
            log.debug("Значение name пустое. В качестве имени используется логин");
        }
    }
}
