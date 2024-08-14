package com.waut.cinemalocationservice.movie.dto;

import java.time.Instant;

public record GenreResponse(
        String id,
        String name,
        String description,
        String imageUrl,
        Instant createdAt,
        Instant updatedAt
) {
}
