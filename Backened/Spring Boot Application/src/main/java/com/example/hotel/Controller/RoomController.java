package com.example.hotel.Controller;

import com.example.hotel.DTO.Response;
import com.example.hotel.Service.Interface.IRoomService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rooms")
@AllArgsConstructor
public class RoomController {

    private final IRoomService roomService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addRoom(
            @RequestParam(value = "photo")MultipartFile file,
            @RequestParam(value = "roomType") String roomType,
            @RequestParam(value = "roomPrice") BigDecimal roomPrice,
            @RequestParam(value = "description") String description) {
        Response response = roomService.addNewRoom(file, roomType, roomPrice, description);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/all")
    public ResponseEntity<Response> getAllBookings() {
        Response response = roomService.getAllRooms();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/types")
    public ResponseEntity<List<String>> getRoomTypes() {
        List<String> response = roomService.getAllRoomTypes();
        return ResponseEntity.ok(response);
    }
    @GetMapping("get-by-id/{id}")
    public ResponseEntity<Response> getRoomById(@PathVariable Long id) {
        Response response = roomService.getRoom(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("available-rooms")
    public ResponseEntity<Response> getAvailableRooms() {
        Response response = roomService.getAvailableRooms();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/available-rooms-by-date-and-type")
    public ResponseEntity<Response> availableRoomByDateAndType(
            @RequestParam()@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam()@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam() String roomType) {
        Response response = roomService.getAvailableRoomsByDateAndType(checkInDate, checkOutDate, roomType);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateRoom(@PathVariable Long id, @RequestParam(value = "photo", required = false)MultipartFile file,
                                               @RequestParam(value = "roomType", required = false) String roomType,
                                               @RequestParam(value = "roomPrice", required = false) BigDecimal roomPrice,
                                               @RequestParam(value = "description", required = false) String description) {
        Response response = roomService.updateRoom(id, file, roomType, roomPrice, description);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteRoom(@PathVariable Long id) {
        Response response = roomService.deleteRoom(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
