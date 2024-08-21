package com.waut.cinemalocationservice.exception;

public class SeatNotFoundException extends ItemNotFoundException {
    public SeatNotFoundException(String message) {
        super(message);
    }
}
