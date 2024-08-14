package com.waut.cinemalocationservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

@Builder
@Validated
public record PhoneNumberRequest(
        @NotNull(message = "Number is required")
        @NotBlank(message = "Number is required")
        @Length(min = 8, message = "Number must be at least 8 characters")
        String number
) {
}
