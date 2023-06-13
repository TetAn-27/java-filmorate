package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.sql.*;
import java.util.*;

@Slf4j
@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> getAllFilms() {
        String sql = "SELECT * from films";
        return jdbcTemplate.query(sql, this::mapRowToFilm);
    }

    @Override
    public Optional<Film> postFilm(Film film) {
       SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");
        film.setId(simpleJdbcInsert.executeAndReturnKey(film.toMap()).intValue());
        addGenre(film);
        log.debug("Фильм {} - добавлен", film.getId());
        return Optional.of(film);
    }

    @Override
    public Optional<Film> putFilm(Film film) {
        deleteGenre(film.getId());
        addGenre(film);
        String sqlQuery = "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? " +
                "WHERE film_id = ?";
        int checkNumber = jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        if (checkNumber == 0) {
            log.error("Film с ID {} не был найден для обновления", film.getId());
            throw new NotFoundException("Film с таким ID не был найден");
        }
        log.debug("Фильм {} обновлен", film.getId());
        return Optional.of(getFilmById(film.getId()));
    }

    @Override
    public Film getFilmById(Integer id) {
        String sql = "SELECT * FROM films WHERE film_id = ?";
        log.debug("Фильм с id: {}", id);
        return jdbcTemplate.queryForObject(sql, this::mapRowToFilm, id);
    }

    private Film mapRowToFilm(ResultSet rs, int rowNum) throws SQLException {
        return Film.builder()
                .id(rs.getInt("film_id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .releaseDate(rs.getDate("release_date").toLocalDate())
                .duration(rs.getInt("duration"))
                .mpa(getMpa(rs.getInt("mpa_id")))
                .genres(getGenre(rs.getInt("film_id")))
                .likeList(getUserIdsWhoLikedFilm(rs.getInt("film_id")))
                .build();
    }

    @Override
    public List<Integer>  getUserIdsWhoLikedFilm(Integer id) {
        String sql = "SELECT * FROM likes WHERE film_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> getLikeId(rs), id);
    }

    @Override
    public void addLike(Integer id, Integer userId) {
        String sqlQuery = "INSERT INTO likes(film_id, user_id) " +
                "values (?, ?)";
        jdbcTemplate.update(sqlQuery,
                id,
                userId);
    }

    @Override
    public void deleteLike(Integer id, Integer userId) {
        String sqlQuery = "DELETE FROM likes where film_id = ? AND user_id = ?";
        jdbcTemplate.update(sqlQuery,
                            id,
                            userId);
    }

    public void addGenre(Film film) {
        if (film.getGenres() != null) {
            Set<Genre> genresSet = new HashSet<>(film.getGenres());
            List<Genre> genres = new ArrayList<>(genresSet);
            jdbcTemplate.batchUpdate("INSERT INTO film_genre(film_id, genre_id) values (?, ?)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setInt(1, film.getId());
                            ps.setInt(2, genres.get(i).getId());
                        }

                        @Override
                        public int getBatchSize() {
                            return genres.size();
                        }
                    });
        }
    }

    public void deleteGenre(int id) {
        String sqlQuery = "DELETE FROM film_genre WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery,
                id);
    }

    private Integer getLikeId(ResultSet rs) throws SQLException {
        return rs.getInt("user_id");
    }

    private List<Genre> getGenre(Integer id) {
        String sql = "SELECT * FROM genres AS g LEFT OUTER JOIN film_genre AS fg ON fg.genre_id = g.genre_id " +
                "where film_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs), id);
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        int id = rs.getInt("genre_id");
        String name = rs.getString("name");
        return new Genre(id, name);
    }

    private Mpa getMpa(Integer id) {
        String sql = "SELECT * FROM ratings WHERE mpa_id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeMpa(rs), id);
    }

    private Mpa makeMpa(ResultSet rs) throws SQLException {
        int id = rs.getInt("mpa_id");
        String name = rs.getString("name");
        return new Mpa(id, name);
    }
}
