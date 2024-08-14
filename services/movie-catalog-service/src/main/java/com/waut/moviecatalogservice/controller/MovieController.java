package com.waut.moviecatalogservice.controller;

import com.waut.moviecatalogservice.dto.MovieRequest;
import com.waut.moviecatalogservice.dto.MovieResponse;
import com.waut.moviecatalogservice.service.MovieService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/catalog/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    @ResponseStatus(CREATED)
    @CircuitBreaker(name = "movie-service", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "movie-service")
    @Retry(name = "movie-service")
    public String createMovie(
            @RequestBody @Valid MovieRequest movieRequest
    ) {
        return movieService.createMovie(movieRequest);
    }

    @GetMapping
    @ResponseStatus(OK)
    @CircuitBreaker(name = "movie-service", fallbackMethod = "moviesFetchFallback")
    @TimeLimiter(name = "movie-service")
    @Retry(name = "movie-service")
    public List<MovieResponse> findAll() {
        return movieService.findAll();
    }

    @GetMapping("/{movie-id}")
    @ResponseStatus(OK)
    @CircuitBreaker(name = "movie-service", fallbackMethod = "movieFetchFallback")
    @TimeLimiter(name = "movie-service")
    @Retry(name = "movie-service")
    public MovieResponse findById(
            @PathVariable("movie-id") String movieId
    ) {
        return movieService.findById(movieId);
    }

    @GetMapping("/ids")
    @ResponseStatus(OK)
    @CircuitBreaker(name = "movie-service", fallbackMethod = "moviesFetchFallback")
    @TimeLimiter(name = "movie-service")
    @Retry(name = "movie-service")
    public List<MovieResponse> findByIds(
            @RequestParam(value = "id") List<String> movieIds
    ) {
        return movieService.findByIds(movieIds);
    }

    @DeleteMapping("/{movie-id}")
    @ResponseStatus(OK)
    @CircuitBreaker(name = "movie-service", fallbackMethod = "movieDeleteFallback")
    @TimeLimiter(name = "movie-service")
    @Retry(name = "movie-service")
    public boolean deleteById(
            @PathVariable("movie-id") String movieId
    ) {
        return movieService.deleteById(movieId);
    }

    public String fallbackMethod(MovieRequest movieRequest, RuntimeException runtimeException) {
        return "Oops! Something went wrong, could not create movie, please try again after some time!";
    }

    public String moviesFetchFallback(RuntimeException runtimeException) {
        return "Oops! Something went wrong, could not fetch movies, please try again after some time!";
    }

    public String moviesFetchFallback(String movieId, RuntimeException runtimeException) {
        return "Oops! Something went wrong, could not fetch movie with ID:: " + movieId +" , please try again after some time!";
    }

    public boolean movieDeleteFallback(String movieId, RuntimeException runtimeException) {
        return false;
    }
}
