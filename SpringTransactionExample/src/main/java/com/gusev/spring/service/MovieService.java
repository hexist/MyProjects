package com.gusev.spring.service;

import com.gusev.spring.exception.MovieNotFoundException;
import com.gusev.spring.model.Movie;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

public interface MovieService {

    Movie createMovie(Movie movie);
    List<Movie> createListMovies(List<Movie> movies);
    void deleteMovie(Movie movie) throws MovieNotFoundException;
    Movie getMovieByTitle(String title);
    List<Movie> listMovies();
}
