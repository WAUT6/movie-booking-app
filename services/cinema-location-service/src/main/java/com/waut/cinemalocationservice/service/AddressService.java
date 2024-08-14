package com.waut.cinemalocationservice.service;

import com.waut.cinemalocationservice.dto.AddressRequest;
import com.waut.cinemalocationservice.exception.ExceptionUtils;
import com.waut.cinemalocationservice.model.Address;
import com.waut.cinemalocationservice.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public Address createAddress(AddressRequest addressRequest) {
        Address address = addressMapper.toAddress(addressRequest);

        return ExceptionUtils.<Address>saveItemOrThrowDuplicateKeyException(() -> addressRepository.save(address));
    }
}
