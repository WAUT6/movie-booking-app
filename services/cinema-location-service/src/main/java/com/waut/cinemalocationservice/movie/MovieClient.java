package com.waut.cinemalocationservice.movie;

import com.waut.cinemalocationservice.movie.dto.MovieResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@FeignClient(
        name = "movie-service",
        url = "${application.config.movie-service-url}/movies"
)
public interface MovieClient {

    @GetMapping("/ids")
    Optional<List<MovieResponse>> findMoviesByIds(@RequestParam(value = "id") List<String> movieIds);
}
