package com.gusev.spring.dao;

import com.gusev.spring.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcMovieDao implements MovieDao {

    @Autowired
    private DataSource dataSource;

    @Override
    public Integer createMovie(Movie movie) {
        String sql = "INSERT INTO movies (movie_title, movie_descr) VALUES (?, ?)";
        int movieId = 0;
        Connection connection = null;
        try{
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, movie.getTitle());
            ps.setString(2, movie.getDescription());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next()){
                movieId = keys.getInt(1);
            }
            keys.close();
            ps.close();
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            if(connection != null){
                try{
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return movieId;
    }

    @Override
    public void deleteMovie(Movie movie) {
        String sql = "DELETE FROM movies where movie_id = ?";
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, movie.getId());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Movie getMovieByTitle(String title) {
        String sql = "SELECT * FROM movies WHERE movie_title = ?";
        Movie movie = null;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                movie = new Movie();
                movie.setId(rs.getInt("movie_id"));
                movie.setTitle(rs.getString("movie_title"));
                movie.setDescription(rs.getString("movie_descr"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return movie;
    }
}
