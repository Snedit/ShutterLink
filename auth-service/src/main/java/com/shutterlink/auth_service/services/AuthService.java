package com.shutterlink.auth_service.services;

 
import com.shutterlink.auth_service.DTO.*;
import com.shutterlink.auth_service.entity.Auth;
import com.shutterlink.auth_service.entity.OTP;
import com.shutterlink.auth_service.repository.AuthRepository;
import com.shutterlink.auth_service.repository.OtpRepository;
import com.shutterlink.auth_service.utils.EmailUtil;
import com.shutterlink.auth_service.utils.JwtUtil;
import com.shutterlink.auth_service.utils.OtpUtil;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final JwtUtil jwtUtil;
    private final OtpUtil otpUtil;
    private final EmailUtil emailUtil;
    @Autowired
    private final OtpRepository otpRepository;
    
    @Transactional
    public AuthResponseDTO register(@Valid RegisterRequestDTO req) {
         if(authRepository.existsByEmail(req.getEmail()))
         {
            throw new RuntimeException("Email already exists");
         }

         Auth user  = new Auth();
         user.setUsername(req.getUsername());
         user.setEmail(req.getEmail());
         user.setPassword(BCrypt.hashpw( req.getPassword() , BCrypt.gensalt()));
         user.setFullname(req.getFullname());
         authRepository.save(user);
         OTP newOtp = otpUtil.generateOtp(req.getEmail());
        // save the otp in db
        otpRepository.save(newOtp);

        emailUtil.sendOtpMail(user.getEmail(), newOtp.getOtp());


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

    @Transactional
    public boolean verifyOtp(VerifYOtp verify) {

        String email  = verify.getEmail();
        String otp  = verify.getOTP();
        
        if(! authRepository.existsByEmail(email) || !otpRepository.existsByEmail(email))
            throw new RuntimeException("Invalid Email!");
            Auth user = authRepository.findByEmail(email).orElse(null);
            OTP otpInRepo = otpRepository.findByEmail(email).orElseThrow();

            if(otpInRepo.getOtp().equals(otp))
            {   
                System.out.println("OTP IN REPO : " + otpInRepo.getOtp());
                System.out.println("OTP received : " + otp);

                if(otpInRepo.getExpiration().isAfter(LocalDateTime.now()))    
            {   
                user.setVerified(true);
                authRepository.save(user);
                    return true;}
                else
                {
            System.out.println("OTP has been expired!");
                    return false;
                }
            }
            else{
                return false;
            }

        


    }

  @Transactional
public String resendOtp(String email) {
    System.out.println(authRepository.findAll());
    System.out.println("Received email : " + email);
    Optional<Auth> optionalUser = authRepository.findByEmail(email);

    if (optionalUser.isEmpty()) {
        return "Register yourself first!";
    }

    Auth user = optionalUser.get();

    if (user.isVerified()) {
        return "Email has already been verified!";
    }

    OTP newOtp = otpUtil.generateOtp(user.getEmail());
    OTP otp = otpRepository.findByEmail(email).orElse(new OTP());
    otp.setEmail(email);
    otp.setOtp(newOtp.getOtp());
    otp.setExpiration(newOtp.getExpiration());
    otpRepository.save(otp);

    emailUtil.sendOtpMail(email, newOtp.getOtp());
    return "A new OTP has been sent to your email.";
}

   

}