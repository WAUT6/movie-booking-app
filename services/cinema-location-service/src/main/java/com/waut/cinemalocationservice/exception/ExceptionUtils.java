package com.waut.cinemalocationservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ExceptionUtils {
//    public static String parseDuplicateKey(String errorMessage) {
//        String patterString = "dup key: \\{ (\\w+): \"([^\"]+)\" }";
//        Pattern pattern = Pattern.compile(patterString);
//        Matcher matcher = pattern.matcher(errorMessage);
//
//        log.error("Error message: {}", errorMessage);
//        if (matcher.find()) {
//            String key = matcher.group(1);
//            String value = matcher.group(2);
//            return "Duplicate key: " + key + " with value: " + value;
//        } else {
//            return "Duplicate value!";
//        }
//    }

    public static <T> T saveItemOrThrowDuplicateKeyException(SaveCallback<T> saveMovieCallback) throws DuplicateKeyException {
        try {
            return saveMovieCallback.save();
        } catch (DataIntegrityViolationException e) {
            log.error(e.getClass().getName());
            throw new DuplicateKeyException("Duplicate value!");
        }
    }
}
