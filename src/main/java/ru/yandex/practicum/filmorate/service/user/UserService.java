package ru.yandex.practicum.filmorate.service.user;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Qualifier("userDbStorage") @NonNull private UserStorage userStorage;

    public User getUserById(Integer id) {
        try {
            return userStorage.getUserById(id);
        } catch (DataAccessException ex) {
            log.error("User с ID {} не был найден", id);
            throw new NotFoundException("User с таким ID не был найден");
        }
    }

    public List<User> getFriends(Integer id) {
        return converterFriends(userStorage.getFriendsList(id));
    }

    public void addFriend(Integer id, Integer friendId) {
        try {
            userStorage.addFriend(id, friendId);
        } catch (DataAccessException ex) {
            log.error("User с ID {} не был найден", id);
            throw new NotFoundException("User с таким ID не был найден");
        }
    }

    public void deleteFriend(Integer id, Integer friendId) {
        try {
            userStorage.deleteFriend(id, friendId);
        } catch (DataAccessException ex) {
            log.error("User с ID {} не был найден", id);
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
        return userStorage.postUser(user);
    }

    public Optional<User> putUser(User user) {
        try {
            user = validatorName(user);
            return userStorage.putUser(user);
        } catch (DataAccessException ex) {
            log.error("User с ID {} не был найден для обновления", user.getId());
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
