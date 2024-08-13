package com.waut.userservice.controller;

import com.waut.userservice.dto.UserRequest;
import com.waut.userservice.dto.UserResponse;
import com.waut.userservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(CREATED)
    @CircuitBreaker(name = "user-service", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "user-service")
    @Retry(name = "user-service")
    public String createUser(@RequestBody @Valid UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @GetMapping("/{user-id}")
    @ResponseStatus(OK)
    @CircuitBreaker(name = "user-service", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "user-service")
    @Retry(name = "user-service")
    public UserResponse findById(
            @PathVariable("user-id") String userId
    ) {
        return userService.findById(userId);
    }

    @PutMapping("/{user-id}")
    @ResponseStatus(ACCEPTED)
    @CircuitBreaker(name = "user-service", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "user-service")
    @Retry(name = "user-service")
    public void updateUser(
            @PathVariable("user-id") String userId,
            @RequestBody @Valid UserRequest userRequest
    ) {
        userService.updateUser(userId, userRequest);
    }

    @DeleteMapping("/{user-id}")
    @ResponseStatus(OK)
    @CircuitBreaker(name = "user-service", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "user-service")
    @Retry(name = "user-service")
    public void deleteUser(
            @PathVariable("user-id") String userId
    ) {
        userService.deleteUser(userId);
    }

    public String fallbackMethod(UserRequest userRequest, RuntimeException runtimeException) {
        return "Oops! Something went wrong, please try again after some time!";
    }

}
