package com.example.hotel.Repository;

import com.example.hotel.Entity.ForgotPass;
import com.example.hotel.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForgotPassRepo extends JpaRepository<ForgotPass, Long> {
    ForgotPass findByToken(String token);
    Optional<ForgotPass> findByUser(UserEntity user);
}
