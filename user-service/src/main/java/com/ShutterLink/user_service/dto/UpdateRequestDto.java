package com.ShutterLink.user_service.dto;

import com.ShutterLink.user_service.enums.UserRole;
import com.ShutterLink.user_service.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateRequestDto {
    @NotNull
    private String username;
    @NotNull
    private String bio;
    @NotNull
    private String profilePictureUrl;

    private UserRole role = UserRole.USER;

    private UserStatus status = UserStatus.ACTIVE;

}
