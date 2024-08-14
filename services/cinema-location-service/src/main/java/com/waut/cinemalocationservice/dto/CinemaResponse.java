package com.waut.cinemalocationservice.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CinemaResponse(
        Integer id,
        String name,
        AddressResponse address,
        List<PhoneNumberResponse> phoneNumbers,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
