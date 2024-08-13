package com.waut.moviecatalogservice.exception;

import com.mongodb.MongoWriteException;
import com.waut.moviecatalogservice.model.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ExceptionUtils {
    public static String parseDuplicateKey(String errorMessage) {
        String patterString = "dup key: \\{ (\\w+): \"([^\"]+)\" }";
        Pattern pattern = Pattern.compile(patterString);
        Matcher matcher = pattern.matcher(errorMessage);

        log.error("Error message: {}", errorMessage);
        if (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2);
            return "Duplicate key: " + key + " with value: " + value;
        } else {
            return "Duplicate value!";
        }
    }

    public static <T> String saveItemOrThrowDuplicateKeyException(SaveCallback<T> saveMovieCallback) throws DuplicateKeyException, ItemNotFoundException {
        try {
            return saveMovieCallback.save();
        } catch (DuplicateKeyException e) {
            if (e.getCause() instanceof MongoWriteException writeException) {
                throw new DuplicateKeyException(parseDuplicateKey(writeException.getError().getMessage()));
            }
            throw new DuplicateKeyException(parseDuplicateKey(e.getMessage()));
        }
    }
}
