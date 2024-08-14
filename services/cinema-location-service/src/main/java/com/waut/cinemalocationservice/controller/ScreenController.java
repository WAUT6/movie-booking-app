package com.waut.cinemalocationservice.controller;

import com.waut.cinemalocationservice.dto.ScreenRequest;
import com.waut.cinemalocationservice.dto.ScreenResponse;
import com.waut.cinemalocationservice.service.ScreenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/cinemas/screens")
@RequiredArgsConstructor
public class ScreenController {

    private final ScreenService screenService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer createScreen(
            @RequestBody @Valid ScreenRequest screenRequest
    ) {
        return screenService.createScreen(screenRequest);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<ScreenResponse> findAll() {
        return screenService.findAll();
    }

    @GetMapping("/{screen-id}")
    @ResponseStatus(OK)
    public ScreenResponse findById(@PathVariable("screen-id") Integer screenId) {
        return screenService.findById(screenId);
    }

    @DeleteMapping("/{screen-id}")
    @ResponseStatus(OK)
    public boolean deleteById(@PathVariable("screen-id") Integer screenId) {
        return screenService.deleteById(screenId);
    }

}
