package com.shutterlink.auth_service.DTO;

import com.shutterlink.auth_service.entity.OTP;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String username;
    private String email;
    private String role;
}
