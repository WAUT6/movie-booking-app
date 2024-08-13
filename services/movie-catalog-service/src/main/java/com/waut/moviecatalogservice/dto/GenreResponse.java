package com.waut.moviecatalogservice.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record GenreResponse(
        String id,
        String name,
        String description,
        String imageUrl,
        Instant createdAt,
        Instant updatedAt
) {
}
