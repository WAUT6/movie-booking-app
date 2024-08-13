package com.waut.moviecatalogservice.exception;

@FunctionalInterface
public interface SaveCallback<T> {

    String save();

}

