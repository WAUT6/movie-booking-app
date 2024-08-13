package com.waut.cinemalocationservice.service;

import com.waut.cinemalocationservice.dto.CinemaRequest;
import com.waut.cinemalocationservice.model.Address;
import com.waut.cinemalocationservice.model.Cinema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CinemaMapper {

    private final PhoneNumberMapper phoneNumberMapper;
    private final AddressMapper addressMapper;

    public Cinema toCinema(CinemaRequest cinemaRequest) {
        return Cinema.builder()
                .name(cinemaRequest.name())
                .phoneNumbers(cinemaRequest.phoneNumbers().stream().map(phoneNumberMapper::toPhoneNumber).collect(Collectors.toList()))
                .address(addressMapper.toAddress(cinemaRequest.addressRequest()))
                .build();
    }

    public Cinema toCinemaWithoutPhoneNumbers(CinemaRequest cinemaRequest, Address address) {
        return Cinema.builder()
                .name(cinemaRequest.name())
                .address(address)
                .build();
    }
}
