package com.waut.cinemalocationservice.exception;

public class MultipleScreenIdsException extends ItemNotFoundException {

    public MultipleScreenIdsException(String message) {
        super(message);
    }
}
