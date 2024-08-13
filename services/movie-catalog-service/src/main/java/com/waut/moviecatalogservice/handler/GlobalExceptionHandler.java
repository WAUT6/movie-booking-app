package com.waut.moviecatalogservice.handler;

import com.waut.moviecatalogservice.exception.GenreNotFoundException;
import com.waut.moviecatalogservice.exception.MovieNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GenreNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public String handle(GenreNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(MovieNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public String handle(MovieNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(CONFLICT)
    public String handle(DuplicateKeyException e) {
        return e.getMessage();
    }

}
