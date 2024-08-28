package com.example.hotel.Controller;

import com.example.hotel.DTO.LoginRequest;
import com.example.hotel.DTO.Response;
import com.example.hotel.Entity.UserEntity;
import com.example.hotel.Service.Interface.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody UserEntity user) {
        Response response = userService.register(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        Response response = userService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
