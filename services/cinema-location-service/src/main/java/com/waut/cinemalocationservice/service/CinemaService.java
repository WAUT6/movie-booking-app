package com.waut.cinemalocationservice.service;

import com.waut.cinemalocationservice.dto.CinemaRequest;
import com.waut.cinemalocationservice.dto.CinemaResponse;
import com.waut.cinemalocationservice.exception.CinemaNotFoundException;
import com.waut.cinemalocationservice.exception.ExceptionUtils;
import com.waut.cinemalocationservice.model.Address;
import com.waut.cinemalocationservice.model.Cinema;
import com.waut.cinemalocationservice.repository.CinemaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CinemaService {

    private final AddressService addressService;
    private final PhoneNumberService phoneNumberService;
    private final CinemaRepository cinemaRepository;
    private final CinemaMapper cinemaMapper;

    @Transactional
//    Rollback the transaction if an exception is thrown
    public Integer createCinema(CinemaRequest cinemaRequest) {
//        Save the address first because it is a dependency for the cinema
        try {
            Address address = addressService.createAddress(cinemaRequest.addressRequest());
            //        Map the cinema request to a cinema object
            Cinema cinema = cinemaMapper.toCinemaWithoutPhoneNumbers(cinemaRequest, address);

//        Save the cinema object
            Cinema savedCinema = ExceptionUtils.<Cinema>saveItemOrThrowDuplicateKeyException(() -> cinemaRepository.save(cinema));

//        Save the phone numbers as they are not a direct dependency of the cinema
            phoneNumberService.createPhoneNumbers(cinemaRequest.phoneNumbers(), savedCinema);
            return savedCinema.getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Cinema findCinemaById(Integer cinemaId) {
        return cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new CinemaNotFoundException("Cinema not found"));
    }

    public List<CinemaResponse> findAll() {
        List<Cinema> cinemas = cinemaRepository.findAll();
        return cinemas.stream().map(cinemaMapper::toCinemaResponse).toList();
    }

    public CinemaResponse findById(Integer cinemaId) {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new CinemaNotFoundException("Cinema not found"));

        return cinemaMapper.toCinemaResponse(cinema);
    }

    public boolean deleteById(Integer cinemaId) {
        if (!cinemaRepository.existsById(cinemaId)) {
            throw new CinemaNotFoundException("Cinema not found");
        }
        cinemaRepository.deleteById(cinemaId);
        return true;
    }
}
