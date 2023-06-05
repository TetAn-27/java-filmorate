package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Component("userDbStorage")
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<User> getAllUsers() {
        String sql = "select * from users";
        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
        return users;
    }

    @Override
    public void postUser(User user) {
        String sqlQuery = "insert into users(name, login, email, birthday) " +
                "values (?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday());
    }

    @Override
    public void putUser(User user) {
        String sqlQuery = "update employees set name = ?, login = ?, email = ?, birthday = ? " +
            "where id = ?";
        jdbcTemplate.update(sqlQuery,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());
    }

    public User getUserById(Integer id) {
        String sql = "select * from users where user_id = ?";
        return (User) jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs), id);
    }

    private User makeUser(ResultSet rs) throws SQLException {
        // используем конструктор, методы ResultSet
        Integer id = rs.getInt("user_id");
        String name = rs.getString("name");
        String login = rs.getString("login");
        String email = rs.getString("email");
        //Set<Integer> friends = rs.getObject("email");

        // Получаем дату и конвертируем её из sql.Date в time.LocalDate
        LocalDate birthday = rs.getDate("birthday").toLocalDate();

        return new User(id, name, login, email, birthday);
    }
}
