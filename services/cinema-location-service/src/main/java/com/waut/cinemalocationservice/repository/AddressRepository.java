package com.waut.cinemalocationservice.repository;

import com.waut.cinemalocationservice.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
