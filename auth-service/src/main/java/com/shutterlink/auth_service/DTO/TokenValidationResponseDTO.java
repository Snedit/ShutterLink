package com.shutterlink.auth_service.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenValidationResponseDTO {
    private boolean valid;
    private String userId;
    private String username;
    private String role;
}
