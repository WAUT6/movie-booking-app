package com.waut.cinemalocationservice.service;

import com.waut.cinemalocationservice.dto.AddressRequest;
import com.waut.cinemalocationservice.model.Address;
import org.springframework.stereotype.Service;

@Service
public class AddressMapper {
    public Address toAddress(AddressRequest addressRequest) {
        return Address.builder()
                .street(addressRequest.street())
                .city(addressRequest.city())
                .country(addressRequest.country())
                .building(addressRequest.building())
                .zipCode(addressRequest.zipCode())
                .state(addressRequest.state())
                .build();
    }
}
