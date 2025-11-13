package com.ShutterLink.user_service.service;

import com.ShutterLink.user_service.dto.CreateRequestDto;
import com.ShutterLink.user_service.dto.UpdateRequestDto;
import com.ShutterLink.user_service.dto.UserDto;
import com.ShutterLink.user_service.dto.UserResponseDto;
import com.ShutterLink.user_service.exception.UserAlreadyExistsException;
import com.ShutterLink.user_service.exception.UserNotFoundException;
import com.ShutterLink.user_service.model.User;
import com.ShutterLink.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.net.CacheRequest;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto createUser(CreateRequestDto userRequestDto) {

        if(userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new UserAlreadyExistsException("Email already in use");
        }

        User user = User.builder()
                .username(userRequestDto.getUsername())
                .email(userRequestDto.getEmail())
                .fullName(userRequestDto.getFullName())
                .passwordHash(userRequestDto.getPasswordHash())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);
        return UserResponseDto.builder()
                .id(savedUser.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public UserDto getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .bio(user.getBio())
                .profilePictureUrl(user.getProfilePictureUrl())
                .role(user.getRole())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .bio(user.getBio())
                .profilePictureUrl(user.getProfilePictureUrl())
                .role(user.getRole())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .bio(user.getBio())
                .profilePictureUrl(user.getProfilePictureUrl())
                .role(user.getRole())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public UserDto updateUser(UUID id, UpdateRequestDto updateRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));


        user.setPasswordHash(updateRequestDto.getPasswordHash());
        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);

        return UserDto.builder()
                .id(updatedUser.getId())
                .username(updatedUser.getUsername())
                .email(updatedUser.getEmail())
                .fullName(updatedUser.getFullName())
                .bio(updatedUser.getBio())
                .profilePictureUrl(updatedUser.getProfilePictureUrl())
                .role(updatedUser.getRole())
                .status(updatedUser.getStatus())
                .createdAt(updatedUser.getCreatedAt())
                .updatedAt(updatedUser.getUpdatedAt())
                .build();
    }

    public void deleteUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        userRepository.delete(user);
    }
}
