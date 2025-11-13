 package com.shutterlink.auth_service.controllers;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.shutterlink.auth_service.DTO.AuthResponseDTO;
import com.shutterlink.auth_service.DTO.LoginRequestDTO;
import com.shutterlink.auth_service.DTO.RegisterRequestDTO;
import com.shutterlink.auth_service.DTO.ResendOtpDTO;
import com.shutterlink.auth_service.DTO.TokenRefreshRequestDTO;
import com.shutterlink.auth_service.DTO.TokenValidationResponseDTO;
import com.shutterlink.auth_service.DTO.VerifYOtp;
import com.shutterlink.auth_service.services.AuthService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp (@Valid @RequestBody VerifYOtp verify) {

        if(authService.verifyOtp(verify))
        {
                return new ResponseEntity<>("OTP has been verified", HttpStatus.CREATED);
            }  
            else
            {
            return new ResponseEntity<>("OTP has not verified", HttpStatus.NOT_ACCEPTABLE);
        }
    }
    
    @PostMapping("/resend-otp")
    public ResponseEntity<String> resendOtp(@Valid @RequestBody ResendOtpDTO email) {
        // your logic   
        // check if email already verified in the main "users" DB

        // here ill jus check if   the field is false or not in temp

            
            return new ResponseEntity<>(authService.resendOtp(email.getEmail()), HttpStatus.OK);

    }

    
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refreshToken(@RequestBody TokenRefreshRequestDTO req) {
        return ResponseEntity.ok(authService.refreshToken(req));
    }

    @GetMapping("/me")
    public ResponseEntity<TokenValidationResponseDTO> validateToken(@RequestHeader("Authorization") String tokenHeader) {
        return ResponseEntity.ok(authService.validateToken(tokenHeader));
    }

   
}
