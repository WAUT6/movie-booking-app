package com.waut.cinemalocationservice.dto;

import com.waut.cinemalocationservice.model.Cinema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ScreenResponse(
        Integer id,
        Cinema cinema,
        String screenNumber,
        Integer capacity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
