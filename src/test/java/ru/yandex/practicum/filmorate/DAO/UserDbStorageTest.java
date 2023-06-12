package ru.yandex.practicum.filmorate.DAO;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageTest {

    private final UserDbStorage userStorage;
    private User user;

    @BeforeEach
    public void beforeEach() {
        List<Integer> friends = new ArrayList<>();
        LocalDate birthday = LocalDate.of(2000,1,27);
        user = new User(1, "name", "login", "email", birthday, friends);
    }

    @Test
    void testGetAllUsers() {
        List<Integer> friends = new ArrayList<>();
        LocalDate birthday = LocalDate.of(2000,1,27);
        User user1 = new User(2, "name1", "login1", "email1", birthday, friends);
        userStorage.postUser(user);
        userStorage.postUser(user1);
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user1);
        List<User> usersActual = userStorage.getAllUsers();
        assertEquals(users, usersActual);

    }

    /*@Test
    void testPostUser() {
        User userActual = userStorage.postUser(user).get();
        assertEquals(user, userActual);
    }

    @Test
    void testPutUser() {
        userStorage.postUser(user);
        user.setEmail("@email");
        user.setLogin("nigol");
        User userActual = userStorage.putUser(user).get();
        assertEquals(user, userActual);
    }

    @Test
    void testGetUserById() {
        userStorage.postUser(user);
        User userActual = userStorage.getUserById(1);
        assertEquals(user, userActual);
    }*/
}