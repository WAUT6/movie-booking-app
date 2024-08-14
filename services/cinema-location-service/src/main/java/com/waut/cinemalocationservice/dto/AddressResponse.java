package com.waut.cinemalocationservice.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AddressResponse(
        Integer id,
        String street,
        String city,
        String country,
        String building,
        String state,
        String zipCode,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
