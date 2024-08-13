package com.waut.moviecatalogservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Builder
public record MovieRequest(
        @NotNull(message = "Title is mandatory")
        @NotBlank(message = "Title is mandatory")
        String title,
        @NotNull(message = "Description is mandatory")
        @NotBlank(message = "Description is mandatory")
        String description,
        @NotNull(message = "Image URL is mandatory")
        @NotEmpty(message = "Image URL is mandatory")
        @URL(message = "Invalid URL")
        String imageUrl,
        @NotNull(message = "Duration is mandatory")
        @Positive(message = "Duration must be positive")
        Integer durationMinutes,
        @NotNull(message = "Release date is mandatory")
        @NotEmpty(message = "Release date is mandatory")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @JsonFormat(pattern = "MM/dd/yyyy")
        String releaseDate,
        @NotNull(message = "Director is mandatory")
        @NotBlank(message = "Director is mandatory")
        String director,
        @NotNull(message = "Cast is mandatory")
        @NotEmpty(message = "Cast is mandatory")
        List<String> cast,
        @NotNull(message = "Genres is mandatory")
        @NotEmpty(message = "Genres is mandatory")
        List<String> genres
) {
}
