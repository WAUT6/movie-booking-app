package com.waut.cinemalocationservice.service;

import com.waut.cinemalocationservice.dto.PhoneNumberRequest;
import com.waut.cinemalocationservice.model.Cinema;
import com.waut.cinemalocationservice.model.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberMapper {
    public PhoneNumber toPhoneNumber(PhoneNumberRequest phoneNumberRequest, Cinema cinema) {
        return PhoneNumber.builder()
                .phoneNumber(phoneNumberRequest.number())
                .cinema(cinema)
                .build();
    }

    public PhoneNumber toPhoneNumber(PhoneNumberRequest phoneNumberRequest) {
        return PhoneNumber.builder()
                .phoneNumber(phoneNumberRequest.number())
                .build();
    }
}
