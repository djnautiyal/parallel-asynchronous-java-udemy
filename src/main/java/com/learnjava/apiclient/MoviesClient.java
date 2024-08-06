package com.learnjava.apiclient;

import com.learnjava.domain.movie.Movie;
import com.learnjava.domain.movie.MovieInfo;
import com.learnjava.domain.movie.Review;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.util.concurrent.CompletableFuture.allOf;


public class MoviesClient {

    private WebClient webClient;

    public MoviesClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Movie retrieveMovie(Long movieInfoId) {
        MovieInfo movieInfo = invokeMovieInfoService(movieInfoId);
        List<Review> review = invokeReviewsService(movieInfoId);
        return new Movie(movieInfo, review);
    }

    public List<Movie> retrieveMovies(List<Long> movieInfoIds) {
        return movieInfoIds.stream()
                .map(this::retrieveMovie)
                .collect(Collectors.toList());
    }

    public CompletableFuture<Movie> retrieveMovieCompletableFuture(Long movieInfoId) {
        CompletableFuture<MovieInfo> movieInfo = CompletableFuture.supplyAsync(() -> invokeMovieInfoService(movieInfoId));
        CompletableFuture<List<Review>> reviews = CompletableFuture.supplyAsync(() -> invokeReviewsService(movieInfoId));

        return movieInfo
                .thenCombine(reviews, Movie::new);
    }

    public List<Movie> retrieveMoviesCompletableFuture(List<Long> movieInfoIds) {
        List<CompletableFuture<Movie>> movieFutures = movieInfoIds.stream()
                .map(this::retrieveMovieCompletableFuture)
                .collect(Collectors.toList());

        CompletableFuture<Void> allFuture =CompletableFuture
            .allOf(movieFutures.toArray(new CompletableFuture[movieFutures.size()]));

        return allFuture.thenApply(v -> movieFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList())
        ).join();
    }

    public List<Movie> retrieveMoviesCompletableFutureAllOf(List<Long> movieInfoIds) {
        var movieFutures = movieInfoIds.stream()
                .map(this::retrieveMovieCompletableFuture)
                .collect(Collectors.toList());

        return movieFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    private List<Review> invokeReviewsService(Long movieInfoId) {
        var reviewUri = UriComponentsBuilder.fromUriString("/v1/reviews")
                .queryParam("movieInfoId", movieInfoId)
                .buildAndExpand()
                .toString();

        return webClient.get()
                .uri(reviewUri)
                .retrieve()
                .bodyToFlux(Review.class)
                .collectList()
                .block();
    }

    private MovieInfo invokeMovieInfoService(Long movieInfoId) {

        String movieUri = "/v1/movie_infos/{movieInfoId}";
        return webClient.get()
                .uri(movieUri, movieInfoId)
                .retrieve()
                .bodyToMono(MovieInfo.class)
                .block();
    }
}
