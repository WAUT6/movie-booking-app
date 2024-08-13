package com.waut.userservice.service;

import com.waut.userservice.dto.UserRequest;
import com.waut.userservice.dto.UserResponse;
import com.waut.userservice.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserMapper {

    public User toUser(UserRequest userRequest) {
        return User.builder()
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .userName(userRequest.userName())
                .email(userRequest.email())
                .password(userRequest.password())
                .phoneNumber(userRequest.phoneNumber())
                .build();
    }

    public UserResponse toUserResponse(User user) {
        log.info(user.getCreatedAt().toString());
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
