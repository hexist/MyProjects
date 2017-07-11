package com.gusev.spring.service;

import com.gusev.spring.dao.MovieDao;
import com.gusev.spring.exception.MovieNotFoundException;
import com.gusev.spring.model.Movie;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(propagation = Propagation.REQUIRED)
public class MovieServiceImpl implements MovieService {

    final static Logger log = Logger.getLogger(MovieServiceImpl.class);

    @Autowired
    MovieDao movieDao;

    @Override
    public Movie createMovie(Movie movie) {
        if (movieDao.getMovieByTitle(movie.getTitle()) != null)
            throw new IllegalArgumentException("A movie with title " + movie.getTitle()
                    + " already exists");
        int id = movieDao.createMovie(movie);
        log.info("Movie " + id + " : " + movie.getTitle() + " created.");
        return movie;
    }

    @Override
    public List<Movie> createListMovies(List<Movie> movies) {
        for (Movie movie : movies) {
            createMovie(movie);
        }
        return movies;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = MovieNotFoundException.class)
    public void deleteMovie(Movie movie) throws MovieNotFoundException {
        if (movieDao.getMovieByTitle(movie.getTitle()) == null)
                throw new MovieNotFoundException("A movie with title " + movie.getTitle()
                        + " doesn't exist in the table", movie.getTitle());
        movieDao.deleteMovie(movie);
        log.info("Movie " + movie.getTitle() + " deleted.");
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Movie getMovieByTitle(String title) {
        return movieDao.getMovieByTitle(title);
    }

}
