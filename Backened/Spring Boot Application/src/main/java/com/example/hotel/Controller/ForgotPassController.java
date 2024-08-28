package com.example.hotel.Controller;

import com.example.hotel.Service.Implementation.ForgotPassService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forgotPassword")
@AllArgsConstructor
public class ForgotPassController {

    private final ForgotPassService forgotPassService;

    @PostMapping("/verifyMail/{email}")
    public ResponseEntity<?> verifyMail(@PathVariable String email) {
        if (forgotPassService.sendOTP(email)) {
            return ResponseEntity.ok("Email Sent Successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User do not exist");
    }

    @PostMapping("/verifyOtp/{token}")
    public ResponseEntity<?> verifyOtp(@PathVariable String token) {
        if(forgotPassService.verifyOTP(token)){
            return ResponseEntity.ok("OTP verified Successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired OTP");
    }
    @PostMapping("/changePassword/{token}/{password}")
    public ResponseEntity<?> changePassword(@PathVariable String token, @PathVariable String password) {
        if(forgotPassService.resetPassword(token, password))
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password changed successfully!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong! Please try again later.");
    }
}
