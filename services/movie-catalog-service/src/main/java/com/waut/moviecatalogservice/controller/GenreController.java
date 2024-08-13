package com.waut.moviecatalogservice.controller;

import com.waut.moviecatalogservice.dto.GenreRequest;
import com.waut.moviecatalogservice.dto.GenreResponse;
import com.waut.moviecatalogservice.service.GenreService;
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
@RequestMapping("/api/v1/catalog/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @PostMapping
    @ResponseStatus(CREATED)
    @CircuitBreaker(name = "genre-service", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "genre-service")
    @Retry(name = "genre-service")
    public String createGenre(
            @RequestBody @Valid GenreRequest genreRequest
    ) {
        return genreService.createGenre(genreRequest);
    }

    @GetMapping
    @ResponseStatus(OK)
    @CircuitBreaker(name = "genre-service", fallbackMethod = "genresFetchFallback")
    @TimeLimiter(name = "genre-service")
    @Retry(name = "genre-service")
    public List<GenreResponse> findAll() {
        return genreService.findAll();
    }

    @GetMapping("/{genre-id}")
    @ResponseStatus(OK)
    @CircuitBreaker(name = "genre-service", fallbackMethod = "genreFetchFallback")
    @TimeLimiter(name = "genre-service")
    @Retry(name = "genre-service")
    public GenreResponse findById(
            @PathVariable("genre-id") String genreId
    ) {
        return genreService.findById(genreId);
    }

    @PutMapping("/{genre-id}")
    @ResponseStatus(OK)
    @CircuitBreaker(name = "genre-service", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "genre-service")
    @Retry(name = "genre-service")
    public void updateById(
            @PathVariable("genre-id") String genreId,
            @RequestBody @Valid GenreRequest genreRequest
    ) {
        genreService.updateById(genreId, genreRequest);
    }

    @DeleteMapping("/{genre-id}")
    @ResponseStatus(OK)
    @CircuitBreaker(name = "genre-service", fallbackMethod = "genreDeleteFallback")
    @TimeLimiter(name = "genre-service")
    @Retry(name = "genre-service")
    public boolean deleteById(
            @PathVariable("genre-id") String genreId
    ) {
        return genreService.deleteById(genreId);
    }

    public String fallbackMethod(GenreRequest genreRequest, RuntimeException runtimeException) {
        return "Oops! Something went wrong, please try again after some time!";
    }

    public String genresFetchFallback(RuntimeException runtimeException) {
        return "Oops! Something went wrong, could not fetch genres, please try again after some time!";
    }

    public String genreFetchFallback(String genreId, RuntimeException runtimeException) {
        return "Oops! Something went wrong, could not fetch genre with ID:: " + genreId + " , please try again after some time!";
    }

    public boolean genreDeleteFallback(String genreId, RuntimeException runtimeException) {
        return false;
    }
}
