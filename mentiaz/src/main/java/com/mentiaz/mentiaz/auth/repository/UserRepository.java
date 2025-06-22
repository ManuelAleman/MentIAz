package com.mentiaz.mentiaz.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mentiaz.mentiaz.auth.model.User;

public interface UserRepository extends JpaRepository<User, UUID>{
    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

}
