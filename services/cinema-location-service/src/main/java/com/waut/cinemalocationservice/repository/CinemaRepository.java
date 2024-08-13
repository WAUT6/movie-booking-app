package com.waut.cinemalocationservice.repository;

import com.waut.cinemalocationservice.model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Integer> {
}
