package com.shutterlink.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shutterlink.auth_service.entity.OTP;

public interface OtpRepository extends JpaRepository<OTP, Integer>{

    
}
