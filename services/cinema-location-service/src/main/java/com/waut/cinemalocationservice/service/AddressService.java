package com.waut.cinemalocationservice.service;

import com.waut.cinemalocationservice.dto.AddressRequest;
import com.waut.cinemalocationservice.model.Address;
import com.waut.cinemalocationservice.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public Address createAddress(AddressRequest addressRequest) {
        Address address = addressMapper.toAddress(addressRequest);
        return addressRepository.save(address);
    }
}
