package com.example.hotel.Service.Implementation;

import com.example.hotel.DTO.Response;
import com.example.hotel.DTO.RoomDTO;
import com.example.hotel.Entity.RoomEntity;
import com.example.hotel.Exception.HotelException;
import com.example.hotel.Repository.RoomRepo;
import com.example.hotel.Service.Interface.IRoomService;
import com.example.hotel.Service.Utils.ImageService;
import com.example.hotel.Service.Utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomService implements IRoomService {

    private final RoomRepo roomRepo;
    private final ImageService imageService;

    @Override
    public Response addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice, String description) {
        Response response = new Response();
        try {
            String Url = imageService.saveImage(file);
            RoomEntity room = new RoomEntity();
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setRoomPhotoUrl(Url);
            room.setRoomDescription(description);
            RoomEntity savedRoom = roomRepo.save(room);
            RoomDTO RoomDTO = Utils.mapRoomEntityToDTOPlusBookings(savedRoom);
            response.setStatusCode(200);
            response.setRoom(RoomDTO);
            response.setMessage("Room added successfully");
        } catch (Exception e) {
            response.setStatusCode(400);
            response.setMessage("Something went wrong"+e.getMessage());
        }
        return response;
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepo.findDistinctRoomTypes();
    }

    @Override
    public Response getAllRooms() {
        Response response = new Response();
        try {
            List<RoomEntity> rooms = roomRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
            List<RoomDTO> roomDTOS = Utils.mapDTOToRoomEntityList(rooms);
            response.setStatusCode(200);
            response.setRoomList(roomDTOS);
            response.setMessage("All rooms found");
        } catch (Exception e) {
            response.setStatusCode(400);
            response.setMessage("Something went wrong"+e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteRoom(long roomId) {
        Response response = new Response();
        try {
            roomRepo.findById(roomId)
                    .orElseThrow(() -> new HotelException("Room Not Found"));
            roomRepo.deleteById(roomId);
            response.setStatusCode(200);
            response.setMessage("Room deleted successfully");
        } catch (Exception e) {
            response.setStatusCode(400);
            response.setMessage("Something went wrong"+e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateRoom(long roomId, MultipartFile file, String roomType, BigDecimal roomPrice, String description) {
        Response response = new Response();
        if(roomType.equals("undefined") || roomPrice == null || description.equals("undefined")) {
           response.setMessage("Please fill all fields");
           response.setStatusCode(400);
           return response;
        }
        try {
            String Url = null;
            if (file!=null && !file.isEmpty()) {
                Url = imageService.saveImage(file);
            }
            RoomEntity room = roomRepo.findById(roomId)
                    .orElseThrow(() -> new HotelException("Room Not Found"));
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            if (Url!=null)room.setRoomPhotoUrl(Url);
            room.setRoomDescription(description);
            RoomEntity updatedRoom = roomRepo.save(room);
            RoomDTO RoomDTO = Utils.mapRoomEntityToDTO(updatedRoom);
            response.setStatusCode(200);
            response.setRoom(RoomDTO);
            response.setMessage("Room updated successfully");
        } catch (Exception e) {
            response.setStatusCode(400);
            response.setMessage("Something went wrong"+e.getMessage());
        }
        return response;
    }

    @Override
    public Response getRoom(long roomId) {
        Response response = new Response();
        try {
            RoomEntity room = roomRepo.findById(roomId)
                    .orElseThrow(() -> new HotelException("Room Not Found"));
            RoomDTO RoomDTO = Utils.mapRoomEntityToDTOPlusBookings(room);
            response.setStatusCode(200);
            response.setRoom(RoomDTO);
            response.setMessage("Room found");
        } catch (Exception e) {
            response.setStatusCode(400);
            response.setMessage("Something went wrong"+e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAvailableRooms() {
        Response response = new Response();
        try {
            List<RoomEntity> availableRooms = roomRepo.getAllAvailableRooms();
            List<RoomDTO> roomDTOS = Utils.mapDTOToRoomEntityList(availableRooms);
            response.setStatusCode(200);
            response.setRoomList(roomDTOS);
            response.setMessage("Available rooms found");
        } catch (Exception e) {
            response.setStatusCode(400);
            response.setMessage("Something went wrong"+e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        Response response = new Response();
        if(checkInDate.isBefore(LocalDate.now()) || checkOutDate.isBefore(LocalDate.now()) || checkOutDate.isBefore(checkInDate)){
            response.setMessage("Please Choose Correct Date");
            response.setStatusCode(400);
            return response;
        }
        try {
            List<RoomEntity> availableRooms = roomRepo.findAvailableRoomsByDatesAndTypes(checkInDate, checkOutDate, roomType);
            List<RoomDTO> roomDTOS = Utils.mapDTOToRoomEntityList(availableRooms);
            response.setStatusCode(200);
            response.setRoomList(roomDTOS);
            response.setMessage("Room successfully retrieved");
        } catch (Exception e) {
            response.setStatusCode(400);
            response.setMessage("Something went wrong"+e.getMessage());
        }
        return response;
    }
}
