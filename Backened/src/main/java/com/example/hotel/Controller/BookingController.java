package com.example.hotel.Controller;

import com.example.hotel.DTO.Response;
import com.example.hotel.Entity.BookingEntity;
import com.example.hotel.Service.Implementation.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/book-room/{roomId}/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> saveBookings(
            @PathVariable("roomId") Long roomId,
            @PathVariable("userId") Long userId,
            @RequestBody BookingEntity bookingEntity) {
        Response response = bookingService.bookRoom(roomId, userId, bookingEntity);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllBookings() {
        Response response = bookingService.getAllBookings();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/get-by-code/{code}")
    public ResponseEntity<Response> getBookingByCode(@PathVariable String code) {
        Response response = bookingService.findBookingsByConfirmationCode(code);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @DeleteMapping("/cancel/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> cancelBookings(@PathVariable long id) {
        Response response = bookingService.cancelBooking(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
