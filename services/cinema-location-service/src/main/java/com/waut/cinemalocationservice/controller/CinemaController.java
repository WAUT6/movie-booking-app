package com.waut.cinemalocationservice.controller;

import com.waut.cinemalocationservice.dto.CinemaRequest;
import com.waut.cinemalocationservice.service.CinemaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

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

}
