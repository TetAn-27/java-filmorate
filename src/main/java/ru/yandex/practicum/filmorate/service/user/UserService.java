package ru.yandex.practicum.filmorate.service.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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

    public Set<User> getFriends(Integer id) {
        Set<User> friends = new HashSet<>();
        for (Integer friend : userStorage.getUserById(id).getFriends()) {
            friends.add(getUserById(Math.toIntExact(friend)));
        }
        return friends;
    }

    public void addFriend(Integer id, Integer friendId) {
        User user = getUserById(id);
        User friend = getUserById(friendId);

        Set<Integer> friendsUser = user.getFriends();
        friendsUser.add(friendId);
        user.setFriends(friendsUser);

        Set<Integer> friendsUser1 = friend.getFriends();
        friendsUser1.add(id);
        friend.setFriends(friendsUser1);
    }

    public void deleteFriend(Integer id, Integer friendId) {
        User user = getUserById(id);
        User friend = getUserById(friendId);

        Set<Integer> friendsUser = user.getFriends();
        friendsUser.remove(friendId);
        user.setFriends(friendsUser);

        Set<Integer> friendsUser1 = friend.getFriends();
        friendsUser1.remove(id);
        friend.setFriends(friendsUser1);
    }

    public List<User> getListOfMutualFriends(Integer id, Integer friendId) {
        Set<Integer> sort = new HashSet<>(getUserById(id).getFriends());
        sort.retainAll(getUserById(friendId).getFriends());
        List<User> friendsList = new ArrayList<>();
        for (Integer aLong : sort) {
            friendsList.add(userStorage.users.get(aLong));
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

    public User getUserById(Integer id) {
        return userStorage.getUserById(id);
    }
}
