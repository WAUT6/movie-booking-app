package com.waut.userservice.handler;

import com.waut.userservice.exception.UserNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public String handle(UserNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(CONFLICT)
    public String handle(DuplicateKeyException e) {
        return e.getMessage();
    }

}
