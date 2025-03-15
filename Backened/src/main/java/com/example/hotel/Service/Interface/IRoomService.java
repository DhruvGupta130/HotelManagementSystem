package com.example.hotel.Service.Interface;

import com.example.hotel.DTO.Response;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IRoomService {

    Response addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice, String description);
    List<String> getAllRoomTypes();
    Response getAllRooms();
    Response deleteRoom(long roomId);
    Response updateRoom(long roomId, MultipartFile file, String roomType, BigDecimal roomPrice, String description);
    Response getRoom(long roomId);
    Response getAvailableRooms();
    Response getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType);


}
