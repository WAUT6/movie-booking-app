package com.waut.cinemalocationservice.service;

import com.waut.cinemalocationservice.dto.SeatRequest;
import com.waut.cinemalocationservice.dto.SeatResponse;
import com.waut.cinemalocationservice.exception.ExceptionUtils;
import com.waut.cinemalocationservice.exception.MultipleScreenIdsException;
import com.waut.cinemalocationservice.exception.SeatNotFoundException;
import com.waut.cinemalocationservice.model.Screen;
import com.waut.cinemalocationservice.model.Seat;
import com.waut.cinemalocationservice.repository.SeatRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final ScreenService screenService;
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;

    public Integer createSeat(@Valid SeatRequest seatRequest) {
//        Find the screen by the screen ID from the seat request
        Screen screen = screenService.findScreenById(seatRequest.screenId());
        Seat seat = seatMapper.toSeat(seatRequest, screen);

        return ExceptionUtils.<Integer>saveItemOrThrowDuplicateKeyException(() -> seatRepository.save(seat).getId());
    }

    public List<Integer> createSeats(@Valid List<SeatRequest> seatRequests) {
        boolean allSeatsSameScreen = seatRequests.stream()
                .allMatch(seatRequest -> seatRequest.screenId().equals(seatRequests.get(0).screenId()));

        if (!allSeatsSameScreen) {
            throw new MultipleScreenIdsException("All seats must have the same screen ID");
        }

        Screen screen = screenService.findScreenById(seatRequests.get(0).screenId());
        List<Seat> seats = seatRequests.stream().map(seatRequest -> seatMapper.toSeat(seatRequest, screen)).toList();

        return ExceptionUtils.<List<Integer>>saveItemOrThrowDuplicateKeyException(() -> seatRepository.saveAll(seats).stream().map(Seat::getId).toList());
    }

    public List<SeatResponse> findAllByScreenId(Integer screenId) {
        return seatRepository.findByScreen_Id(screenId).stream()
                .map(seatMapper::toSeatResponse)
                .toList();
    }

    public boolean updateSeat(Integer seatId, boolean isAvailable) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new SeatNotFoundException("Seat with id " + seatId + " not found"));

//        If the seat availability is already the same as the new availability, return true(No database update needed)
        if(seat.isAvailable() == isAvailable) {
            return true;
        }

        seat.setAvailable(isAvailable);
        seatRepository.save(seat);
        return true;
    }
}
