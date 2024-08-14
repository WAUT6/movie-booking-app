package com.waut.cinemalocationservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Builder
public record CinemaRequest(
        @NotNull(message = "Name is required")
        @NotBlank(message = "Name is required")
        @Length(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
        String name,
        @NotNull(message = "Address is required")
        AddressRequest addressRequest,
        @NotEmpty(message = "Cinema should have at least one phone number")
        @NotNull(message = "Cinema should have at least one phone number")
        List<PhoneNumberRequest> phoneNumbers,
        List<String> currentMoviesNowShowing
) {
        public CinemaRequest {
                if (currentMoviesNowShowing == null) {
                        currentMoviesNowShowing = List.of();
                }
        }
}
