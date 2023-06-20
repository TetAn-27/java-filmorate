package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Component("userDbStorage")
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "select * from users";
        log.info("Получен список пользователей");
        return jdbcTemplate.query(sql, this::mapRowToUser);
    }

    @Override
    public Optional<User> postUser(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
        user.setId(simpleJdbcInsert.executeAndReturnKey(user.toMap()).intValue());
        log.info("Добавлен новый пользователь: {}", user.getId());
        return Optional.of(user);
    }

    @Override
    public Optional<User> putUser(User user) {
        String sqlQuery = "UPDATE users SET name = ?, login = ?, email = ?, birthday = ? " +
            "WHERE user_id = ?";
        int checkNumber = jdbcTemplate.update(sqlQuery,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());
        if (checkNumber == 0) {
            log.error("User с ID {} не был найден для обновления", user.getId());
            throw new NotFoundException("User с таким ID не был найден");
        }
        log.info("Данные пользователя: {} обновлены", user.getId());
        return Optional.of(user);
    }

    @Override
    public User getUserById(Integer id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        log.debug("User с id: {}", id);
        return jdbcTemplate.queryForObject(sql, this::mapRowToUser, id);
    }

    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getInt("user_id"))
                .name(rs.getString("name"))
                .login(rs.getString("login"))
                .email(rs.getString("email"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .friends(getFriendsList(rs.getInt("user_id")))
                .build();
    }

    @Override
    public List<Integer> getFriendsList(Integer id) {
       String sql = "SELECT * FROM friends WHERE user_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> getFriend(rs), id);
    }

    private Integer getFriend(ResultSet rs) throws SQLException {
        return rs.getInt("friend_id");
    }

    @Override
    public void addFriend(Integer id, Integer friendId) {
        String sqlQuery = "INSERT INTO friends(user_id, friend_id) " +
                          "values (?, ?)";
        jdbcTemplate.update(sqlQuery,
                            id,
                            friendId);
        log.debug("Пользователь с id {} добавил в друзья пользователя {}", id, friendId);
    }

    @Override
    public void deleteFriend(Integer id, Integer friendId) {
        String sqlQuery = "DELETE FROM friends where user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sqlQuery, id, friendId);
        log.debug("Пользователь с id {} из друзей пользователя {}", id, friendId);
    }
}
