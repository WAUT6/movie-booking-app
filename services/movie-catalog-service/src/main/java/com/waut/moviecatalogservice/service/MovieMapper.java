package com.waut.moviecatalogservice.service;

import com.waut.moviecatalogservice.dto.GenreResponse;
import com.waut.moviecatalogservice.dto.MovieRequest;
import com.waut.moviecatalogservice.dto.MovieResponse;
import com.waut.moviecatalogservice.model.Movie;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class MovieMapper {

    public Movie toMovie(MovieRequest movieRequest) {
//        Format the release date to a LocalDate object and then to an Instant object
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate releaseDate = LocalDate.parse(movieRequest.releaseDate(), formatter);
        Instant releaseDateInstant = releaseDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

        return Movie.builder()
                .title(movieRequest.title())
                .director(movieRequest.director())
                .cast(movieRequest.cast())
                .description(movieRequest.description())
                .durationMinutes(movieRequest.durationMinutes())
                .imageUrl(movieRequest.imageUrl())
                .releaseDate(releaseDateInstant)
                .genres(movieRequest.genres())
                .build();
    }

    public MovieResponse toMovieResponse(Movie movie, List<GenreResponse> genreResponses) {
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .durationMinutes(movie.getDurationMinutes())
                .releaseDate(movie.getReleaseDate().toString())
                .director(movie.getDirector())
                .cast(movie.getCast())
                .createdAt(movie.getCreatedAt())
                .updatedAt(movie.getUpdatedAt())
                .imageUrl(movie.getImageUrl())
                .genres(genreResponses)
                .description(movie.getDescription())
                .build();
    }
}
