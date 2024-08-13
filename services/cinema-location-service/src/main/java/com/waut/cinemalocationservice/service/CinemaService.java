package com.waut.cinemalocationservice.service;

import com.waut.cinemalocationservice.dto.CinemaRequest;
import com.waut.cinemalocationservice.dto.PhoneNumberRequest;
import com.waut.cinemalocationservice.model.Address;
import com.waut.cinemalocationservice.model.Cinema;
import com.waut.cinemalocationservice.repository.CinemaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CinemaService {

    private final AddressService addressService;
    private final PhoneNumberService phoneNumberService;
    private final CinemaRepository cinemaRepository;
    private final CinemaMapper cinemaMapper;

    @Transactional
    public Integer createCinema(CinemaRequest cinemaRequest) {
        Address address = addressService.createAddress(cinemaRequest.addressRequest());

        Cinema cinema = cinemaMapper.toCinemaWithoutPhoneNumbers(cinemaRequest, address);
        Cinema savedCinema = cinemaRepository.save(cinema);

        phoneNumberService.createPhoneNumbers(cinemaRequest.phoneNumbers(), savedCinema);

        return savedCinema.getId();
    }
}
