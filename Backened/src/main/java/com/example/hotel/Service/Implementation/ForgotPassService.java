package com.example.hotel.Service.Implementation;

import com.example.hotel.DTO.EmailBody;
import com.example.hotel.Entity.ForgotPass;
import com.example.hotel.Entity.UserEntity;
import com.example.hotel.Repository.ForgotPassRepo;
import com.example.hotel.Repository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class ForgotPassService {

    private final UserRepo userRepository;
    private final ForgotPassRepo tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public ForgotPassService(UserRepo userRepository, ForgotPassRepo tokenRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean sendOTP(String email) {
        Optional<UserEntity> optional= userRepository.findByEmail(email);
        if (optional.isPresent()) {
            EmailBody body = new EmailBody();
            UserEntity user = optional.get();
            Random rand = new Random();
            String token = String.valueOf(rand.nextInt(100000,999999));
            if(tokenRepository.findByUser(user).isPresent()) {
                tokenRepository.delete(tokenRepository.findByUser(user).get());
            }
            ForgotPass resetToken = new ForgotPass();
            resetToken.setToken(token);
            resetToken.setUser(user);
            resetToken.setExpiryDate(new Date(System.currentTimeMillis() + 5 * 60 * 1000)); // 5 minutes
            tokenRepository.save(resetToken);
            emailService.sendEmail(email, "Password Reset OTP", body.verifyBody(resetToken));
            return true;
        }
        return false;
    }

    public boolean verifyOTP(String token) {
        ForgotPass resetToken = tokenRepository.findByToken(token);
        if(resetToken != null){
            if(resetToken.getExpiryDate().after(new Date()))
                return true;
            else {
                tokenRepository.delete(resetToken);
                return false;
            }
        }
        else return false;
    }

    public boolean resetPassword(String token, String newPassword) {
        ForgotPass resetToken = tokenRepository.findByToken(token);
        if (resetToken != null && resetToken.getExpiryDate().after(new Date())) {
            UserEntity user = resetToken.getUser();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            tokenRepository.delete(resetToken);
            return true;
        }
        return false;
    }
}

