package com.gusev.spring;

import com.gusev.spring.exception.MovieNotFoundException;
import com.gusev.spring.model.Movie;
import com.gusev.spring.service.MovieService;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class App {
    final static Logger log = Logger.getLogger(App.class);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring-config.xml"});

        MovieService movieService = context.getBean("movieService", MovieService.class);

//        testAlreadyExist(movieService);
//        testSomeAlreadyExist(movieService);
        testDelete(movieService);
    }

    private static void testAlreadyExist(MovieService movieService) {
        //This movie already exists in the table
        Movie movie = new Movie("Knockin' on Heaven's Door ", "Nice movie!");
        movieService.createMovie(movie);
    }

    private static void testSomeAlreadyExist(MovieService movieService) {
        //This movie already exists in the table
        Movie movie1 = new Movie("Knockin' on Heaven's Door ", "Nice movie!");

        //This movie will not be added to the table
        Movie movie2 = new Movie("Green mile", "Great movie!");

        List<Movie> movies = new ArrayList<Movie>();
        movies.add(movie1);
        movies.add(movie2);

        movieService.createListMovies(movies);
    }

    private static void testDelete(MovieService movieService) {
        //This movie doesn't exist in the table
        Movie movie = new Movie("qwerty", "zxcvbn");

        try {
            movieService.deleteMovie(movie);
        }catch (MovieNotFoundException ex){
            log.info("Can't delete movie with title " + ex.getTitle());
            ex.printStackTrace();
        }
    }



}
