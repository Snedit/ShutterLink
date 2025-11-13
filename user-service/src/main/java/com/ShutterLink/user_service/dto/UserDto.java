package com.ShutterLink.user_service.dto;


import com.ShutterLink.user_service.enums.UserRole;
import com.ShutterLink.user_service.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private UUID id;

    private String username;

    private String email;

    private String fullName;

    private String bio;

    private String profilePictureUrl;

    private UserRole role = UserRole.USER;

    private UserStatus status = UserStatus.ACTIVE;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
