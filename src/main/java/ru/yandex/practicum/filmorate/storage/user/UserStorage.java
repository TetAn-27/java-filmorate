package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Qualifier;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Qualifier("userDbStorage")
public interface UserStorage {
   HashMap<Integer, User> users = new HashMap<>();

    List<User> getAllUsers();

    Optional<User> postUser(User user);

    Optional<User> putUser(User user);

    User getUserById(Integer id);


    List<Integer> getFriendsList(Integer id);

    void addFriend(Integer id, Integer friendId);

    void deleteFriend(Integer id, Integer friendId);
}
