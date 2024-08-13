package com.waut.cinemalocationservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

@Builder
@Validated
public record AddressRequest(
        @NotNull(message = "Street is required")
        @NotBlank(message = "Street is required")
        String street,
        @NotNull(message = "City is required")
        @NotBlank(message = "City is required")
        String city,
        @NotNull(message = "Country is required")
        @NotBlank(message = "Country is required")
        String country,
        @NotNull(message = "Building is required")
        @NotNull(message = "Building is required")
        String building,
        String state,
        String zipCode
) {
}
