package com.waut.moviecatalogservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@Builder
public record GenreRequest(
        @NotNull(message = "Name is required")
        @NotBlank(message = "Name is required")
        @Length(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
        String name,
        @NotNull(message = "Description is required")
        @NotBlank(message = "Description is required")
        @Length(min = 3, max = 50, message = "Description must be between 3 and 50 characters")
        String description,
        @NotNull(message = "Image URL is required")
        @NotBlank(message = "Image URL is required")
        @URL(message = "Invalid URL")
        String imageUrl
) {
}
