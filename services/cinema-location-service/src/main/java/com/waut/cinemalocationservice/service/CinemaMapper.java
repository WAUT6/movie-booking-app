package com.waut.cinemalocationservice.service;

import com.waut.cinemalocationservice.dto.CinemaRequest;
import com.waut.cinemalocationservice.dto.CinemaResponse;
import com.waut.cinemalocationservice.dto.ScreenRequest;
import com.waut.cinemalocationservice.model.Address;
import com.waut.cinemalocationservice.model.Cinema;
import com.waut.cinemalocationservice.model.Screen;
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

    public CinemaResponse toCinemaResponse(Cinema cinema) {
        return CinemaResponse.builder()
                .id(cinema.getId())
                .name(cinema.getName())
                .phoneNumbers(cinema.getPhoneNumbers().stream().map(phoneNumberMapper::toPhoneNumberResponse).collect(Collectors.toList()))
                .address(addressMapper.toAddressResponse(cinema.getAddress()))
                .createdAt(cinema.getCreatedAt())
                .updatedAt(cinema.getUpdatedAt())
                .build();
    }

}
