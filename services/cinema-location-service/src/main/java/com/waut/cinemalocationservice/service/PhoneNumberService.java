package com.waut.cinemalocationservice.service;

import com.waut.cinemalocationservice.dto.PhoneNumberRequest;
import com.waut.cinemalocationservice.model.Cinema;
import com.waut.cinemalocationservice.model.PhoneNumber;
import com.waut.cinemalocationservice.repository.PhoneNumberRepository;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;
    private final PhoneNumberMapper phoneNumberMapper;

    public void createPhoneNumbers(@NotEmpty(message = "Cinema should have at least one phone number") @NotNull(message = "Cinema should have at least one phone number") List<PhoneNumberRequest> phoneNumberRequests, Cinema savedCinema) {
        List<PhoneNumber> phoneNumbers = phoneNumberRequests.stream()
                .map(phoneNumberRequest -> phoneNumberMapper.toPhoneNumber(phoneNumberRequest, savedCinema))
                .toList();

        phoneNumberRepository.saveAll(phoneNumbers);
    }
}
