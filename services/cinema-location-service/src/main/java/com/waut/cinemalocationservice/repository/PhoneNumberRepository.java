package com.waut.cinemalocationservice.repository;

import com.waut.cinemalocationservice.model.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Integer> {
}
