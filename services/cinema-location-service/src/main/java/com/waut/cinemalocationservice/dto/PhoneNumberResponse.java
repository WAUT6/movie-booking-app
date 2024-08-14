package com.waut.cinemalocationservice.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PhoneNumberResponse(
        Integer id,
        String phoneNumber,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
