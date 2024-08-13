package com.waut.moviecatalogservice.exception;

public class GenreNotFoundException extends ItemNotFoundException{

    public GenreNotFoundException(String message) {
        super(message);
    }
}
