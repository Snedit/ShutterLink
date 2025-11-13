package com.shutterlink.auth_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
public class OTP {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    

    String otp;

    String email;

    LocalDateTime expiration;

    

}
