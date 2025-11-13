package com.shutterlink.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shutterlink.auth_service.entity.OTP;
import java.util.List;
import java.util.Optional;


public interface OtpRepository extends JpaRepository<OTP, Integer>{

    public boolean  existsByEmail(String email);

    public Optional<OTP> findByEmail(String email);
    
}
