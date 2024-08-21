package com.waut.cinemalocationservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Builder
public record SeatRequest(
        @NotNull(message = "Screen ID is required")
        @Positive(message = "Screen ID must be positive")
        Integer screenId,
        @NotNull(message = "Row number is required")
        @PositiveOrZero(message = "Row number must be positive")
        Integer rowNumber,
        @NotNull(message = "Column number is required")
        @PositiveOrZero(message = "Column number must be positive")
        Integer columnNumber,
        boolean isAvailable
) {
}
