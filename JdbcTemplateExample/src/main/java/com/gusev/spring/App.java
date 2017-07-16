package com.gusev.spring;

import com.gusev.spring.model.Movie;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {

    private static final Logger log = Logger.getLogger(App.class);

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        JdbcTemplate jdbcTemplate = (JdbcTemplate) context.getBean("jdbcTemplate");

//        testQueryForObject(jdbcTemplate);
//        testQueryForComplexObject(jdbcTemplate);
//        testInsertWithFeedback(jdbcTemplate);
//        testQueryForListObjects(jdbcTemplate);
//        testButchQuery(jdbcTemplate);
        testSimpleJdbcInsert(jdbcTemplate);
    }

    private static void testQueryForObject(JdbcTemplate jdbcTemplate) {
        int countMovies = jdbcTemplate.queryForObject("select count(*) from  movies where movie_id > ?", Integer.class, 1);
        log.debug("Movies with id > 1 count: " + countMovies);

        String movieDescription = jdbcTemplate.queryForObject("select movie_descr from movies where movie_id = ? and movie_title = ?",
                new Object[]{1, "Knockin' on Heaven's Door"}, String.class);
        log.debug("Knockin' on Heaven's Door description: " + movieDescription);
    }

    private static void testQueryForComplexObject(JdbcTemplate jdbcTemplate) {
        Movie movie = jdbcTemplate.queryForObject("select * from movies where movie_id = ?", new Object[]{1}, new MovieRowMapper());
        log.debug("Movie with id 1: " + movie);
    }

    private static void testInsertWithFeedback(JdbcTemplate jdbcTemplate) {
        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory("insert into movies (movie_title, movie_descr) values (?, ?) ", Types.VARCHAR, Types.VARCHAR);
        factory.setGeneratedKeysColumnNames("movie_id");
        PreparedStatementCreator preparedStatementCreator = factory.newPreparedStatementCreator(new Object[]{"Back to the Future",
                "17 year old Marty McFly got home early last night. 30 years early."});

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int count = jdbcTemplate.update(preparedStatementCreator, keyHolder);
        log.debug("Inserted rows count: " + count);
        log.debug("Movie's id: " + keyHolder.getKey().intValue());
    }


    private static void testQueryForListObjects(JdbcTemplate jdbcTemplate) {
        List<Movie> listMovies = jdbcTemplate.query("select * from movies", new MovieRowMapper());
        for (Movie movie : listMovies){
            log.debug(movie);
        }
    }

    private  static void testButchQuery(JdbcTemplate jdbcTemplate){
        final List<Movie> listMovies = new ArrayList<Movie>();
        listMovies.add(new Movie("Movie_1", "Description_1"));
        listMovies.add(new Movie("Movie_2", "Description_2"));
        listMovies.add(new Movie("Movie_3", "Description_3"));

        int[] ints = jdbcTemplate.batchUpdate("insert into movies (movie_title, movie_descr) values (?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, listMovies.get(i).getTitle());
                preparedStatement.setString(2, listMovies.get(i).getDescription());
            }

            @Override
            public int getBatchSize() {
                return listMovies.size();
            }
        });

        for (int count: ints){
            log.debug("Number of rows affected by each statement: " + count);
        }
    }

    private static void testSimpleJdbcInsert(JdbcTemplate jdbcTemplate){

        SimpleJdbcInsert insertMovie = new SimpleJdbcInsert(jdbcTemplate).withTableName("movies").usingGeneratedKeyColumns("movie_id");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("movie_title", "The Godfather");
        params.put("movie_descr", "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.");

        Number id = insertMovie.executeAndReturnKey(params);

        log.debug("Movie's id: " + id);
    }



    private static class MovieRowMapper implements RowMapper<Movie> {

        @Override
        public Movie mapRow(ResultSet resultSet, int i) throws SQLException {

            Movie movie = new Movie();
            movie.setId(resultSet.getInt("movie_id"));
            movie.setTitle(resultSet.getString("movie_title"));
            movie.setDescription("movie_desr");
            return movie;
        }
    }
}
