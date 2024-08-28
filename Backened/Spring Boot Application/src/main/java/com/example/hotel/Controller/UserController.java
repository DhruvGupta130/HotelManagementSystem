package com.example.hotel.Controller;

import com.example.hotel.DTO.Response;
import com.example.hotel.Entity.UserEntity;
import com.example.hotel.Service.Implementation.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUsers() {
        Response response = userService.getAllUsers();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable long id) {
        Response response = userService.getUserById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteUserById(@PathVariable Long id) {
        Response response = userService.deleteUser(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/get-profile-info")
    public ResponseEntity<Response> getProfileInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Response response = userService.getUserInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/get-user-bookings/{id}")
    public ResponseEntity<Response> getUserBooking(@PathVariable long id) {
        Response response = userService.getUserBookingHistory(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> updateUser(@RequestParam long id, @RequestBody UserEntity user) {
        Response response = userService.updateUser(id, user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
