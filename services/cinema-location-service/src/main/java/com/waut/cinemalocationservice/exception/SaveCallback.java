package com.waut.cinemalocationservice.exception;

@FunctionalInterface
public interface SaveCallback<T> {

    T save();

}

