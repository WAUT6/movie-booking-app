package com.waut.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record UserRequest(
        @NotNull(message = "User first name is required")
        @Length(min = 2, max = 50, message = "User first name must be between 2 and 50 characters")
        String firstName,
        @NotNull(message = "User last name is required")
        @Length(min = 2, max = 50, message = "User last name must be between 2 and 50 characters")
        String lastName,
        @NotNull(message = "User username is required")
        @Length(min = 2, max = 50, message = "User username must be between 2 and 50 characters")
        String userName,
        @NotNull(message = "User email is required")
        @Email
        String email,
        @NotNull(message = "User password is required")
        @Length(min = 8, max = 50, message = "User password must be between 8 and 50 characters")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "User password must contain at least one uppercase letter, one lowercase letter, and one number")
        String password,
        @NotNull(message = "User phone number is required")
        @NotEmpty(message = "User phone number is required")
        String phoneNumber
) {
}
