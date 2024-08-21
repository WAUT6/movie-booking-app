package com.waut.cinemalocationservice.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SeatResponse(
        Integer id,
        int rowNumber,
        int columnNumber,
        boolean isAvailable,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
