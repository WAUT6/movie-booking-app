package com.waut.cinemalocationservice.controller;

import com.waut.cinemalocationservice.dto.SeatRequest;
import com.waut.cinemalocationservice.dto.SeatResponse;
import com.waut.cinemalocationservice.service.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/cinemas/screens/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer createSeat(
            @RequestBody @Valid SeatRequest seatRequest
    ) {
        return seatService.createSeat(seatRequest);
    }

    @PostMapping("/bulk")
    @ResponseStatus(CREATED)
    public List<Integer> createSeats(
            @RequestBody @Valid List<SeatRequest> seatRequests
    ) {
        return seatService.createSeats(seatRequests);
    }

    @GetMapping("/screen/{screen-id}")
    @ResponseStatus(OK)
    public List<SeatResponse> findAllByScreenId(@PathVariable("screen-id") Integer screenId) {
        return seatService.findAllByScreenId(screenId);
    }

    @PutMapping("/{seat-id}")
    @ResponseStatus(OK)
    public boolean updateSeatAvailability(
            @PathVariable("seat-id") Integer seatId,
            @RequestBody boolean isAvailable
    ) {
        return seatService.updateSeat(seatId, isAvailable);
    }
}
