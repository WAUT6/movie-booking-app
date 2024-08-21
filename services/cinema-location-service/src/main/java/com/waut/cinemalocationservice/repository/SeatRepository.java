package com.waut.cinemalocationservice.repository;

import com.waut.cinemalocationservice.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Integer> {

    List<Seat> findByScreen_Id(@NonNull Integer id);
}
