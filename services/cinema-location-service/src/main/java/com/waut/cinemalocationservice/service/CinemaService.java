package com.waut.cinemalocationservice.service;

import com.waut.cinemalocationservice.dto.CinemaRequest;
import com.waut.cinemalocationservice.dto.CinemaResponse;
import com.waut.cinemalocationservice.exception.CinemaNotFoundException;
import com.waut.cinemalocationservice.exception.ExceptionUtils;
import com.waut.cinemalocationservice.exception.MoviesNotFoundException;
import com.waut.cinemalocationservice.model.Address;
import com.waut.cinemalocationservice.model.Cinema;
import com.waut.cinemalocationservice.movie.MovieClient;
import com.waut.cinemalocationservice.movie.dto.MovieResponse;
import com.waut.cinemalocationservice.repository.CinemaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CinemaService {

    private final AddressService addressService;
    private final PhoneNumberService phoneNumberService;
    private final CinemaRepository cinemaRepository;
    private final CinemaMapper cinemaMapper;
    private final MovieClient movieClient;

    @Transactional
//    Rollback the transaction if an exception is thrown
    public Integer createCinema(CinemaRequest cinemaRequest) {
//        Save the address first because it is a dependency for the cinema
            Address address = addressService.createAddress(cinemaRequest.addressRequest());
            //        Map the cinema request to a cinema object
            Cinema cinema = cinemaMapper.toCinemaWithoutPhoneNumbers(cinemaRequest, address);

            //        Get the movie ids from the request
            List<String> movieIds = cinemaRequest.currentMoviesNowShowing();

            if (movieIds != null && !movieIds.isEmpty()) {
                //        Get the movies from the movie service
                List<MovieResponse> movieResponses = movieClient.findMoviesByIds(movieIds).orElseThrow(() -> new MoviesNotFoundException("Movies not found!"));

                //        Check if the number of movies returned is the same as the number of movie ids
                if(movieResponses.size() != movieIds.size()) {
                    log.info(movieResponses.stream().map(movieResponse -> "Movie ID: " + movieResponse.id() + " Title: " + movieResponse.title()).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString());
                    log.info(movieIds.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString());
                    throw new MoviesNotFoundException("Movies not found");
                }

                //        Set the current movies now showing
                cinema.setCurrentMoviesNowShowing(movieResponses.stream().map(MovieResponse::id).toList());
            }

//        Save the cinema object
            Cinema savedCinema = ExceptionUtils.<Cinema>saveItemOrThrowDuplicateKeyException(() -> cinemaRepository.save(cinema));

//        Save the phone numbers as they are not a direct dependency of the cinema
            phoneNumberService.createPhoneNumbers(cinemaRequest.phoneNumbers(), savedCinema);
            return savedCinema.getId();
    }

    public Cinema findCinemaById(Integer cinemaId) {
        return cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new CinemaNotFoundException("Cinema not found"));
    }

    public List<CinemaResponse> findAll() {
        List<Cinema> cinemas = cinemaRepository.findAll();
        return cinemas.stream().map(cinemaMapper::toCinemaResponse).toList();
    }

    public CinemaResponse findById(Integer cinemaId) {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new CinemaNotFoundException("Cinema not found"));

        return cinemaMapper.toCinemaResponse(cinema);
    }

    public boolean deleteById(Integer cinemaId) {
        if (!cinemaRepository.existsById(cinemaId)) {
            throw new CinemaNotFoundException("Cinema not found");
        }
        cinemaRepository.deleteById(cinemaId);
        return true;
    }

    public boolean updateCurrentMoviesNowShowing(Integer cinemaId, List<String> movieIds) {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new CinemaNotFoundException("Cinema not found"));

        List<MovieResponse> movieResponses = movieClient.findMoviesByIds(movieIds).orElseThrow(() -> new MoviesNotFoundException("Movies not found"));

        if(movieResponses.size() != movieIds.size()) {
            throw new MoviesNotFoundException("Movies not found");
        }

        cinema.setCurrentMoviesNowShowing(new ArrayList<>(movieResponses.stream().map(MovieResponse::id).toList()));
        log.info(movieResponses.stream().map(movieResponse -> "Movie ID: " + movieResponse.id() + " Title: " + movieResponse.title()).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString());
        cinemaRepository.save(cinema);
        return true;
    }
}
