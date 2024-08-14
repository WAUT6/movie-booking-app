package com.waut.cinemalocationservice.controller;

import com.waut.cinemalocationservice.dto.CinemaRequest;
import com.waut.cinemalocationservice.dto.CinemaResponse;
import com.waut.cinemalocationservice.service.CinemaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/cinemas")
@RequiredArgsConstructor
public class CinemaController {

    private final CinemaService cinemaService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer createCinema(@RequestBody @Valid CinemaRequest cinemaRequest) {
            return cinemaService.createCinema(cinemaRequest);
    }

    @PutMapping("/{cinema-id}/movies/now-showing")
    @ResponseStatus(OK)
    public boolean updateCurrentMoviesNowShowing(@PathVariable("cinema-id") Integer cinemaId, @RequestBody List<String> movieIds) {
        return cinemaService.updateCurrentMoviesNowShowing(cinemaId, movieIds);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<CinemaResponse> findAll() {
        return cinemaService.findAll();
    }

    @GetMapping("/{cinema-id}")
    @ResponseStatus(OK)
    public CinemaResponse findById(@PathVariable("cinema-id") Integer cinemaId) {
        return cinemaService.findById(cinemaId);
    }

    @DeleteMapping("/{cinema-id}")
    @ResponseStatus(OK)
    public boolean deleteById(@PathVariable("cinema-id") Integer cinemaId) {
        return cinemaService.deleteById(cinemaId);
    }

}
