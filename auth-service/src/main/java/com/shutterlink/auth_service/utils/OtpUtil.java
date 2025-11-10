package com.shutterlink.auth_service.utils;

import org.springframework.stereotype.Component;

import com.shutterlink.auth_service.entity.OTP;

import java.security.SecureRandom;
import java.time.LocalDateTime;
@Component
public class OtpUtil {
    

    private final int expirySeconds  = 180; 
    private final int otpLength  = 5; 
    

    private String generateString()
    {
        StringBuilder otp = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for(int i = 1 ; i<= otpLength; i++)
        {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }


    public OTP generateOtp(String email)
    {
        String otp = generateString();
        LocalDateTime expiryTime = LocalDateTime.now().plusSeconds(expirySeconds);
        
        OTP generatedOTP = new OTP();
        generatedOTP.setEmail(email);
        generatedOTP.setExpiration(expiryTime);
        generatedOTP.setOtp(otp);
        return generatedOTP;
    }

    
}
