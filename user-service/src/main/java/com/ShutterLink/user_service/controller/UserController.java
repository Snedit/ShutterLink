package com.ShutterLink.user_service.controller;

import com.ShutterLink.user_service.dto.*;
import com.ShutterLink.user_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    UserService userService;


    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/users")
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details.")
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(
            @Valid @RequestBody CreateRequestDto createRequestDto) {

        // Implementation goes here
        UserResponseDto userResponseDto = userService.createUser(createRequestDto);
        return ResponseEntity.ok().body(ApiResponse.success(
                userResponseDto,
                "User created successfully",
                HttpStatus.CREATED,
                "/api/users"
        ));
    }

    @GetMapping("/api/users/{id}")
    @Operation(summary = "Get user by ID", description = "Fetches a user by their unique ID.")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable UUID id) {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok().body(ApiResponse.success(
                userDto,
                "User fetched successfully",
                HttpStatus.OK,
                "/api/users/" + id
        ));
    }

    @GetMapping("api/users/email/{email}")
    @Operation(summary = "Get user by email", description = "Fetches a user by their email address.")
    public ResponseEntity<ApiResponse<UserDto>> getUserByEmail(@PathVariable String email) {
        UserDto userDto = userService.getUserByEmail(email);
        return ResponseEntity.ok().body(ApiResponse.success(
                userDto,
                "User fetched successfully",
                HttpStatus.OK,
                "/api/users/email/" + email
        ));
    }

    @GetMapping("api/users/username/{username}")
    @Operation(summary = "Get user by username", description = "Fetches a user by their username.")
    public ResponseEntity<ApiResponse<UserDto>> getUserByUsername(@PathVariable String username) {
        UserDto userDto = userService.getUserByUsername(username);
        return ResponseEntity.ok().body(ApiResponse.success(
                userDto,
                "User fetched successfully",
                HttpStatus.OK,
                "/api/users/username/" + username
        ));
    }

    @PutMapping("/api/users/{id}")
    @Operation(summary = "Update user", description = "Updates the details of an existing user.")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateRequestDto updateRequestDto) {

        UserDto userDto = userService.updateUser(id, updateRequestDto);
        return ResponseEntity.ok().body(ApiResponse.success(
                userDto,
                "User updated successfully",
                HttpStatus.OK,
                "/api/users/" + id
        ));
    }

    @DeleteMapping("/api/users/{id}")
    @Operation(summary = "Delete user", description = "Deletes a user by their unique ID.")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().body(ApiResponse.success(
                null,
                "User deleted successfully",
                HttpStatus.OK,
                "/api/users/" + id
        ));
    }
}
