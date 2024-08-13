package com.waut.userservice.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record UserResponse(
        String id,
        String firstName,
        String lastName,
        String userName,
        String email,
        String phoneNumber,
        Instant createdAt,
        Instant updatedAt
) {
}
