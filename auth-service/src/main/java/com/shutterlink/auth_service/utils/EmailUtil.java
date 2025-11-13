package com.shutterlink.auth_service.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailUtil {
    
    private final JavaMailSender javaMailSender;
    private static final Logger logger =
        LoggerFactory.getLogger(com.shutterlink.auth_service.utils.EmailUtil.class);
    public void sendOtpMail(String to, String OTP)
    {
   
            SimpleMailMessage emailMsg = new SimpleMailMessage();
            emailMsg.setTo(to);
            emailMsg.setSubject("Your ShutterLink OTP");
            emailMsg.setText("Your OTP is : " + OTP + " \nIt will expire in 10 minutes! ");
            javaMailSender.send(emailMsg);
            logger.info("OTP sent to {} ", to);
       
        

    }




}
