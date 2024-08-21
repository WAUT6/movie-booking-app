package com.waut.cinemalocationservice.service;

import com.waut.cinemalocationservice.dto.SeatRequest;
import com.waut.cinemalocationservice.dto.SeatResponse;
import com.waut.cinemalocationservice.model.Screen;
import com.waut.cinemalocationservice.model.Seat;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class SeatMapper {


    public Seat toSeat(@Valid SeatRequest seatRequest, Screen screen) {
        return Seat.builder()
                .screen(screen)
                .rowNumber(seatRequest.rowNumber())
                .columnNumber(seatRequest.columnNumber())
                .isAvailable(seatRequest.isAvailable())
                .build();
    }

    public SeatResponse toSeatResponse(Seat seat) {
        return SeatResponse.builder()
                .id(seat.getId())
                .rowNumber(seat.getRowNumber())
                .columnNumber(seat.getColumnNumber())
                .isAvailable(seat.isAvailable())
                .createdAt(seat.getCreatedAt())
                .updatedAt(seat.getUpdatedAt())
                .build();
    }
}
