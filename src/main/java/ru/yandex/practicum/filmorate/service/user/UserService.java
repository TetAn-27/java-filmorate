package ru.yandex.practicum.filmorate.service.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private UserStorage userStorage;

    public User getUserById(Integer id) {
        return userStorage.getUserById(id);
    }

    public List<User> getFriends(Integer id) {
        List<User> friends = new ArrayList<>();
        Set<Integer> friendsList = getUserById(id).getFriends();
        for (Integer friend : friendsList) {
            friends.add(getUserById((friend)));
        }
        return friends;
    }

    public void addFriend(Integer id, Integer friendId) {
        User user = getUserById(id);
        User friend = getUserById(friendId);

        user.getFriends().add(friendId);
        friend.getFriends().add(id);
    }

    public void deleteFriend(Integer id, Integer friendId) {
        User user = getUserById(id);
        User friend = getUserById(friendId);

        if (user.getFriends().contains(friendId)) {
            user.getFriends().remove(friendId);
            friend.getFriends().remove(id);
        } else {
            throw new NotFoundException("Пользователи с такими id не являются друзьями");
        }
    }

    public List<User> getListOfMutualFriends(Integer id, Integer friendId) {
        User user = getUserById(id);
        User friend = getUserById(friendId);

        Set<Integer> sort = new HashSet<>(user.getFriends());
        sort.retainAll(friend.getFriends());
        List<User> friendsList = new ArrayList<>();
        for (Integer i : sort) {
            friendsList.add(getUserById(i));
        }
        return friendsList;
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User postUser(User user) {
        return userStorage.postUser(user);
    }

    public User putUser(User user, HttpServletResponse response) {
        return userStorage.putUser(user, response);
    }
}
