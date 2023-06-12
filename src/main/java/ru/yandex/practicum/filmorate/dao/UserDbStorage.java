package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
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
        return jdbcTemplate.query(sql, this::mapRowToUser);
    }

    @Override
    public Optional<User> postUser(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
        int id = simpleJdbcInsert.executeAndReturnKey(user.toMap()).intValue();
        log.info("Добавлен новый пользователь: {}", user.getId());
        return Optional.of(getUserById(id));
    }

    @Override
    public Optional<User> putUser(User user) {
        String sqlQuery = "update users set name = ?, login = ?, email = ?, birthday = ? " +
            "where user_id = ?";
        jdbcTemplate.update(sqlQuery,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());
        return Optional.of(getUserById(user.getId()));
    }

    @Override
    public User getUserById(Integer id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
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
    }

    @Override
    public void deleteFriend(Integer id, Integer friendId) {
        String sqlQuery = "DELETE FROM friends where user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sqlQuery, id, friendId);
    }
}
