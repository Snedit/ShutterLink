package com.shutterlink.auth_service.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResendOtpDTO {
@NotNull(message = "email cant be null")
@Email(message = "invalid email format")
private String email;    
}
