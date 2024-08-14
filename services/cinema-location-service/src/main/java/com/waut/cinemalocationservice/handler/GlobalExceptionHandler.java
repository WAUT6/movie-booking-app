package com.waut.cinemalocationservice.handler;

import com.waut.cinemalocationservice.exception.CinemaNotFoundException;
import com.waut.cinemalocationservice.exception.MoviesNotFoundException;
import com.waut.cinemalocationservice.exception.ScreenNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CinemaNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public String handle(CinemaNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ScreenNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public String handle(ScreenNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(MoviesNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public String handle(MoviesNotFoundException e) {
        e.printStackTrace();
        return e.getMessage();
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(CONFLICT)
    public String handle(DuplicateKeyException e) {
        return e.getMessage();
    }

//    Explicitly add this handler because it cannot be caught inside a transaction
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(CONFLICT)
    public String handle(DataIntegrityViolationException e) {
       if (e.getCause() instanceof ConstraintViolationException) {
           return "Duplicate value!";
       }
        return e.getMessage();
    }
}
