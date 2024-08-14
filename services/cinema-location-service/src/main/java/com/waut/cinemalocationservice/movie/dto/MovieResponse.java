package com.waut.cinemalocationservice.movie.dto;

import java.time.Instant;
import java.util.List;

public record MovieResponse(
        String id,
        String title,
        Integer durationMinutes,
        String releaseDate,
        String director,
        List<String> cast,
        Instant createdAt,
        Instant updatedAt,
        String imageUrl,
        List<GenreResponse> genres,
        String description
) {
}
