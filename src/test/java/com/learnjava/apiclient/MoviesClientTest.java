package com.learnjava.apiclient;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import com.learnjava.domain.movie.Movie;

import java.util.List;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;
import static org.junit.jupiter.api.Assertions.*;


class MoviesClientTest {
    WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080/movies")
            .build();
    MoviesClient moviesClient = new MoviesClient(webClient);
    @RepeatedTest(10)
    void retrieveMovie() {
        startTimer();
        Movie movie = moviesClient.retrieveMovie(1L);

        timeTaken();stopWatchReset();

        log(movie.toString());
        assertEquals("Batman Begins", movie.getMovieInfo().getName());

        assert movie.getReviewList().size()==1;
    }

    @RepeatedTest(10)
    void retrieveMovieCompletableFuture() {
        startTimer();
        Movie movie = moviesClient.retrieveMovieCompletableFuture(1L)
                .join();

        timeTaken();stopWatchReset();

        log(movie.toString());
        assertEquals("Batman Begins", movie.getMovieInfo().getName());

        assert movie.getReviewList().size()==1;
    }

    @RepeatedTest(10)
    void retrieveMovies() {
        startTimer();
        List<Movie> movies = moviesClient.
                retrieveMovies(List.of(1L,2L,3L,4L, 5L,6L,7L));

        timeTaken();stopWatchReset();

        log(movies.toString());
        assertEquals(7, movies.size());
    }

    @RepeatedTest(10)
    void retrieveMoviesCompletableFuture() {
        startTimer();
        List<Movie> movies = moviesClient.
                retrieveMoviesCompletableFuture(List.of(1L,2L,3L,4L, 5L,6L,7L));

        timeTaken();stopWatchReset();

        log(movies.toString());
        assertEquals(7, movies.size());
    }

    @RepeatedTest(10)
    void retrieveMoviesCompletableFutureAllOf() {
        startTimer();
        List<Movie> movies = moviesClient.
                retrieveMoviesCompletableFutureAllOf(List.of(1L,2L,3L,4L, 5L,6L,7L));

        timeTaken();stopWatchReset();

        log(movies.toString());
        assertEquals(7, movies.size());
    }
}