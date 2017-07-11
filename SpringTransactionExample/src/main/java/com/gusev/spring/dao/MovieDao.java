package com.gusev.spring.dao;

import com.gusev.spring.model.Movie;

public interface MovieDao {

    Integer createMovie(Movie movie);
    void deleteMovie(Movie movie);
    Movie getMovieByTitle(String title);

}
