package ru.yandex.practicum.filmorate.service.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private UserStorage userStorage;

    public User getUserById(Integer id) {
        try {
            log.debug("Пользователь с id: {}", id);
            return userStorage.getUserById(id);
        } catch (DataAccessException ex) {
            throw new NotFoundException("User с таким ID не был найден");
        }
    }

    public List<User> getFriends(Integer id) {
        return converterFriends(userStorage.getFriendsList(id));
    }

    public void addFriend(Integer id, Integer friendId) {
        try {
            log.debug("Пользователь с id {} добавил в друзья пользователя {}", id, friendId);
            userStorage.addFriend(id, friendId);
        } catch (DataAccessException ex) {
            throw new NotFoundException("User с таким ID не был найден");
        }
    }

    public void deleteFriend(Integer id, Integer friendId) {
        try {
            userStorage.deleteFriend(id, friendId);
        } catch (DataAccessException ex) {
            throw new NotFoundException("Пользователи с такими id не являются друзьями");
        }
    }

    public List<User> getListOfMutualFriends(Integer id, Integer friendId) {
        List<Integer> sort = new ArrayList<>(userStorage.getFriendsList(id));
        sort.retainAll(userStorage.getFriendsList(friendId));
        return converterFriends(sort);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public Optional<User> postUser(User user) {
        user = validatorName(user);
        log.debug("Пользователь с именем {} создан", user.getName());
        return userStorage.postUser(user);
    }

    public Optional<User> putUser(User user) {
        try {
            log.debug("Пользователь с именем {} обновлен", user.getName());
            user = validatorName(user);
            return userStorage.putUser(user);
        } catch (DataAccessException ex) {
            throw new NotFoundException("User с таким ID не был найден");
        }
    }

    private User validatorName(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
            log.debug("Значение name пустое. В качестве имени будет использоваться login");
        }
        return user;
    }

    private List<User> converterFriends(List<Integer> idList) {
        List<User> friendList = new ArrayList<>();
        for (Integer id : idList) {
            friendList.add(getUserById(id));
        }
        return friendList;
    }
}
