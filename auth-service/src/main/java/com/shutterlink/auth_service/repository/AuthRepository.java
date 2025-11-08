package com.shutterlink.auth_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shutterlink.auth_service.entity.Auth;

public interface AuthRepository extends JpaRepository<Auth, UUID> {
    boolean existsByEmail(String email);
    Optional<Auth> findByEmail(String email);
    Optional<Auth>  findByUsername(String username);
}
