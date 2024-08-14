package com.waut.cinemalocationservice.service;

import com.waut.cinemalocationservice.dto.ScreenRequest;
import com.waut.cinemalocationservice.dto.ScreenResponse;
import com.waut.cinemalocationservice.model.Cinema;
import com.waut.cinemalocationservice.model.Screen;
import org.springframework.stereotype.Service;

@Service
public class ScreenMapper {


    public Screen toScreen(ScreenRequest screenRequest, Cinema cinema) {
        return Screen.builder()
                .cinema(cinema)
                .screenNumber(screenRequest.screenNumber())
                .capacity(screenRequest.capacity())
                .build();
    }

    public ScreenResponse toScreenResponse(Screen screen) {
        return ScreenResponse.builder()
                .id(screen.getId())
                .cinema(screen.getCinema())
                .screenNumber(screen.getScreenNumber())
                .capacity(screen.getCapacity())
                .createdAt(screen.getCreatedAt())
                .updatedAt(screen.getUpdatedAt())
                .build();
    }
}
