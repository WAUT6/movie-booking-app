package com.waut.cinemalocationservice.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ItemNotFoundException extends RuntimeException {
    private final String message;
}
