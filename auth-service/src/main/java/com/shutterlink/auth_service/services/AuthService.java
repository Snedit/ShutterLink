package com.shutterlink.auth_service.services;

 
import com.shutterlink.auth_service.DTO.*;
import com.shutterlink.auth_service.entity.Auth;
import com.shutterlink.auth_service.entity.OTP;
import com.shutterlink.auth_service.repository.AuthRepository;
import com.shutterlink.auth_service.repository.OtpRepository;
import com.shutterlink.auth_service.utils.JwtUtil;
import com.shutterlink.auth_service.utils.OtpUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final JwtUtil jwtUtil;
    private final OtpUtil otpUtil;
    @Autowired
    private final OtpRepository otpRepository;
    

    public AuthResponseDTO register(RegisterRequestDTO req) {
         if(authRepository.existsByEmail(req.getEmail()))
         {
            throw new RuntimeException("Email already exists");
         }

         Auth user  = new Auth();
         user.setUsername(req.getUsername());
         user.setEmail(req.getEmail());
         user.setPassword(BCrypt.hashpw( req.getPassword() , BCrypt.gensalt()));
         authRepository.save(user);
         OTP newOtp = otpUtil.generateOtp(req.getEmail());
        // save the otp in db
         otpRepository.save(newOtp);
         
           String accessToken = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        return new AuthResponseDTO(accessToken, refreshToken, user.getUsername(), user.getEmail(), user.getRole().name()); 

    }

    public AuthResponseDTO login(LoginRequestDTO req) {
       Auth user = authRepository.findByEmail(req.getEmail())
       .orElseThrow(()->new RuntimeException("Email not found"));

       if(!BCrypt.checkpw(req.getPassword(), user.getPassword()))
       throw new RuntimeException("Invalid Password!");


       String accessToken = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        return new AuthResponseDTO(accessToken, refreshToken, user.getUsername(), user.getEmail(), user.getRole().name());
    }

    public AuthResponseDTO refreshToken(TokenRefreshRequestDTO req) {
        String email = jwtUtil.validateRefreshToken(req.getRefreshToken());
        Auth user = authRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = jwtUtil.generateToken(email, user.getRole().name());
        String newRefreshToken = jwtUtil.generateRefreshToken(email);

        return new AuthResponseDTO(newAccessToken, newRefreshToken, user.getUsername(), user.getEmail(), user.getRole().name());
    
    }

    public TokenValidationResponseDTO validateToken(String tokenHeader) {
         String token = tokenHeader.replace("Bearer ", "");
        String email = jwtUtil.validateTokenAndGetEmail(token);
        Auth user = authRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        return new TokenValidationResponseDTO(true, user.getId().toString(), user.getUsername(), user.getRole().name());
   
    }

   public void changePassword(ChangePasswordDTO req) {
        // // You’d typically fetch the user from SecurityContext
        // // For now, assume current user’s email is known
        // String currentEmail = "user@example.com";
        // Auth user = authRepository.findByEmail(currentEmail)
        //         .orElseThrow(() -> new RuntimeException("User not found"));

        // if (!BCrypt.checkpw(req.getOldPassword(), user.getPassword()))
        //     throw new RuntimeException("Incorrect old password");

        // user.setPassword(BCrypt.hashpw(req.getNewPassword(), BCrypt.gensalt()));
        // authRepository.save(user);
    }

}