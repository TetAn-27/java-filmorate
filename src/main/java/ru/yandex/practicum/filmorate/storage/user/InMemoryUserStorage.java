package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
    public Optional<User> postUser(User user) {
        log.debug("Пользователь с именем {} создан", user.getName());
        createUser(user);
        return Optional.of(user);
    }

    @PutMapping()
    public Optional<User> putUser(User user) {
        if (users.containsKey(user.getId())) {
            validatorName(user);
            log.debug("Пользователь с именем {} обновлен", user.getName());
            users.put(user.getId(), user);
            return Optional.of(user);
        } else {
            throw new NotFoundException("User с таким ID не был найден");
        }
    }

    public void createUser(User user) {
        validatorName(user);
        if (user.getId() == 0) {
            user.setId(userId);
            userId++;
        }
        users.put(user.getId(), user);
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

    @Override
    public List<Integer> getFriendsList(Integer id) {
        return users.get(id).getFriends();
    }

    @Override
    public void addFriend(Integer id, Integer friendId) {
        users.get(id).getFriends().add(friendId);
    }

    @Override
    public void deleteFriend(Integer id, Integer friendId) {
        users.get(id).getFriends().remove(friendId);
    }
}
