package com.waut.cinemalocationservice.service;

import com.waut.cinemalocationservice.dto.ScreenRequest;
import com.waut.cinemalocationservice.dto.ScreenResponse;
import com.waut.cinemalocationservice.exception.ExceptionUtils;
import com.waut.cinemalocationservice.exception.ScreenNotFoundException;
import com.waut.cinemalocationservice.model.Cinema;
import com.waut.cinemalocationservice.model.Screen;
import com.waut.cinemalocationservice.repository.ScreenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreenService {

    private final CinemaService cinemaService;
    private final ScreenRepository screenRepository;
    private final ScreenMapper screenMapper;

    public Integer createScreen(ScreenRequest screenRequest) {
        Cinema cinema = cinemaService.findCinemaById(screenRequest.cinemaId());
        Screen screen = screenMapper.toScreen(screenRequest, cinema);

        return ExceptionUtils.<Integer>saveItemOrThrowDuplicateKeyException(() -> screenRepository.save(screen).getId());
    }

    public List<ScreenResponse> findAll() {
        return screenRepository.findAll().stream()
                .map(screenMapper::toScreenResponse)
                .toList();
    }

    public ScreenResponse findById(Integer screenId) {
        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new ScreenNotFoundException("Screen with id " + screenId + " not found"));

        return screenMapper.toScreenResponse(screen);
    }

    public boolean deleteById(Integer screenId) {
        if (screenRepository.existsById(screenId)) {
            screenRepository.deleteById(screenId);
            return true;
        } else {
            throw new ScreenNotFoundException("Screen with id " + screenId + " not found");
        }
    }
}
