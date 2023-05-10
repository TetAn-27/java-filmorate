package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;


import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

   InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();

    @GetMapping()
    public List<User> getAllUsers() {
        return inMemoryUserStorage.getAllUsers();
    }

    @PostMapping()
    public User postUser(@Valid @RequestBody User user) {
        return inMemoryUserStorage.postUser(user);
    }

    @PutMapping()
    public User putUser(@Valid @RequestBody User user, HttpServletResponse response) {
        return inMemoryUserStorage.putUser(user, response);
    }
}
