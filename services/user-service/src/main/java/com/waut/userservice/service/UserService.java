package com.waut.userservice.service;

import com.mongodb.MongoWriteException;
import com.waut.userservice.dto.UserRequest;
import com.waut.userservice.dto.UserResponse;
import com.waut.userservice.exception.UserNotFoundException;
import com.waut.userservice.model.User;
import com.waut.userservice.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public String createUser(@Valid UserRequest userRequest) {
        try {
            return userRepository.save(userMapper.toUser(userRequest)).getId();
        } catch (DuplicateKeyException e) {
            Throwable cause = e.getCause();
            if(cause instanceof MongoWriteException writeException) {
                throw new DuplicateKeyException(parseDuplicateKey(writeException.getError().getMessage()));
            }
            throw new DuplicateKeyException(parseDuplicateKey(e.getMessage()));
        }
    }

    private String parseDuplicateKey(String errorMessage) {
        String patterString = "dup key: \\{ (\\w+): \"([^\"]+)\" }";
        Pattern pattern = Pattern.compile(patterString);
        Matcher matcher = pattern.matcher(errorMessage);

        if (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2);
            return "Duplicate key: " + key + " with value: " + value;
        } else {
            return "Duplicate value!";
        }
    }

    public UserResponse findById(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        log.info(user.get().getCreatedAt().toString());
        return userRepository.findById(userId)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    public void updateUser(String userId, @Valid UserRequest userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        userRepository.save(mergeUser(user, userRequest));
    }

    private User mergeUser(User user, UserRequest userRequest) {
        if(StringUtils.isNotBlank(userRequest.firstName())) {
            user.setFirstName(userRequest.firstName());
        }
        if(StringUtils.isNotBlank(userRequest.lastName())) {
            user.setLastName(userRequest.lastName());
        }
        if(StringUtils.isNotBlank(userRequest.email())) {
            user.setEmail(userRequest.email());
        }
        if(StringUtils.isNotBlank(userRequest.phoneNumber())) {
            user.setPhoneNumber(userRequest.phoneNumber());
        }
        if(StringUtils.isNotBlank(userRequest.password())) {
            user.setPassword(userRequest.password());
        }
        return user;
    }

    public void deleteUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }
}
