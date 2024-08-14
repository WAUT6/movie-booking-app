package com.waut.cinemalocationservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record ScreenRequest(
        @NotNull(message = "Cinema ID is required")
        @Positive(message = "Cinema ID must be positive")
        Integer cinemaId,
        @NotNull(message = "Screen number is required")
        @NotEmpty(message = "Screen number must not be empty")
        String screenNumber,
        @NotNull(message = "Capacity is required")
        @Positive(message = "Capacity must be positive")
        Integer capacity
) {
}
