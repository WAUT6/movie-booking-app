package com.waut.moviecatalogservice.service;

import com.waut.moviecatalogservice.dto.GenreResponse;
import com.waut.moviecatalogservice.dto.MovieRequest;
import com.waut.moviecatalogservice.dto.MovieResponse;
import com.waut.moviecatalogservice.exception.ExceptionUtils;
import com.waut.moviecatalogservice.exception.GenreNotFoundException;
import com.waut.moviecatalogservice.model.Movie;
import com.waut.moviecatalogservice.repository.MovieRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final GenreService genreService;
    private final MovieMapper movieMapper;

    public String createMovie(@Valid MovieRequest movieRequest) {
        boolean isValidGenreIds = genreService.validateGenreIds(movieRequest.genres());
        if (!isValidGenreIds) {
            throw new GenreNotFoundException("Invalid genre ids");
        }
        Movie movie = movieMapper.toMovie(movieRequest);
        return ExceptionUtils.<Movie>saveItemOrThrowDuplicateKeyException(() -> movieRepository.save(movie).getId());
    }


    public List<MovieResponse> findAll() {
        return movieRepository.findAll().stream()
                .map(movie -> {
                    List<GenreResponse> genres = genreService.findAllInByIds(movie.getGenres());
                    return movieMapper.toMovieResponse(movie, genres);
                })
                .toList();
    }

    public MovieResponse findById(String movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new GenreNotFoundException("Movie with id " + movieId + " not found")
        );
        List<GenreResponse> genres = genreService.findAllInByIds(movie.getGenres());
        return movieMapper.toMovieResponse(movie, genres);
    }

    public boolean deleteById(String movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new GenreNotFoundException("Movie with id " + movieId + " not found");
        }
        movieRepository.deleteById(movieId);
        return true;
    }

    public List<MovieResponse> findByIds(List<String> movieIds) {
        log.info("Fetching movies with ids: {}", movieIds);
        return movieRepository.findAllById(movieIds).stream()
                .map(movie -> {
                    List<GenreResponse> genres = genreService.findAllInByIds(movie.getGenres());
                    return movieMapper.toMovieResponse(movie, genres);
                })
                .toList();
    }
}
