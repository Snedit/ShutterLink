package com.ShutterLink.user_service.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRequestDto {
    @NotNull
    private String username;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String passwordHash;

    @NotNull
    private String fullName;
}
