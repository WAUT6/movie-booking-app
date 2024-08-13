package com.waut.moviecatalogservice.exception;

public class MovieNotFoundException extends ItemNotFoundException {
    public MovieNotFoundException(String message) {
        super(message);
    }
}
