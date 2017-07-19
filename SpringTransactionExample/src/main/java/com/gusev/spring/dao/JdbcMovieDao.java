package com.gusev.spring.dao;

import com.gusev.spring.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcMovieDao implements MovieDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Integer createMovie(Movie movie) {
        SimpleJdbcInsert insertMovie = new SimpleJdbcInsert(jdbcTemplate).withTableName("movies")
                .usingGeneratedKeyColumns("movie_id");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("movie_title", movie.getTitle());
        params.put("movie_descr", movie.getDescription());

        Number id = insertMovie.executeAndReturnKey(params);
        return id.intValue();
    }

    @Override
    public void deleteMovie(Movie movie) {
        String sql = "DELETE FROM movies WHERE movie_id = ?";
        jdbcTemplate.update(sql, movie.getId());
    }

    @Override
    public Movie getMovieByTitle(String title) {
        try {
            String sql = "SELECT * FROM movies WHERE movie_title = ?";
            return jdbcTemplate.queryForObject(sql, new MovieRowMapper(), title);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Movie> listMovies() {
        String sql = "SELECT * FROM movies";
        List<Movie> allMovies = jdbcTemplate.query(sql, new MovieRowMapper());
        return allMovies;
    }

    private class MovieRowMapper implements RowMapper<Movie> {

        @Override
        public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
            Movie movie = new Movie();
            movie.setId(resultSet.getInt("movie_id"));
            movie.setTitle(resultSet.getString("movie_title"));
            movie.setDescription(resultSet.getString("movie_descr"));
            return movie;
        }
    }
}
