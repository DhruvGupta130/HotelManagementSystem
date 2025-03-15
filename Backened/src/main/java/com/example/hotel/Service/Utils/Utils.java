package com.example.hotel.Service.Utils;

import com.example.hotel.DTO.BookingDTO;
import com.example.hotel.DTO.RoomDTO;
import com.example.hotel.DTO.UserDTO;
import com.example.hotel.Entity.BookingEntity;
import com.example.hotel.Entity.RoomEntity;
import com.example.hotel.Entity.UserEntity;

import java.util.List;
import java.util.UUID;

public class Utils {

    public static String generateRandomString(int length) {
        return UUID.randomUUID().toString().substring(0, length);
    }

    public static UserDTO mapUserEntityToDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(userEntity.getId());
        userDTO.setName(userEntity.getName());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPhoneNumber(userEntity.getPhoneNumber());
        userDTO.setRole(userEntity.getRole());
        return userDTO;
    }
    public static UserDTO mapUserEntityToDTOPlusBookings(UserEntity userEntity) {
        UserDTO userDTO = mapUserEntityToDTO(userEntity);
        if(!userEntity.getBookings().isEmpty()){
            userDTO.setBookings(userEntity.getBookings().stream().map(bookingEntity -> mapBookingEntityToDTOPlusBookedRoom(bookingEntity, false)).toList());
        }
        return userDTO;
    }
    public static BookingDTO mapBookingEntityToDTOPlusBookedRoom(BookingEntity bookingEntity, boolean isBooked) {
        BookingDTO bookingDTO = mapBookingEntityToDTO(bookingEntity);
        if (isBooked) {
            bookingDTO.setUser(mapUserEntityToDTO(bookingEntity.getUser()));
        }
        if (bookingEntity.getRoom() != null) {
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setId(bookingEntity.getRoom().getId());
            roomDTO.setRoomType(bookingEntity.getRoom().getRoomType());
            roomDTO.setRoomPrice(bookingEntity.getRoom().getRoomPrice());
            roomDTO.setRoomDescription(bookingEntity.getRoom().getRoomDescription());
            roomDTO.setRoomPhotoUrl(bookingEntity.getRoom().getRoomPhotoUrl());
            bookingDTO.setRoom(roomDTO);
        }
        return bookingDTO;
    }
    public static RoomDTO mapRoomEntityToDTO(RoomEntity roomEntity) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(roomEntity.getId());
        roomDTO.setRoomType(roomEntity.getRoomType());
        roomDTO.setRoomPrice(roomEntity.getRoomPrice());
        roomDTO.setRoomPhotoUrl(roomEntity.getRoomPhotoUrl());
        roomDTO.setRoomDescription(roomEntity.getRoomDescription());
        return roomDTO;
    }
    public static RoomDTO mapRoomEntityToDTOPlusBookings(RoomEntity roomEntity) {
        RoomDTO roomDTO = mapRoomEntityToDTO(roomEntity);
        if(!roomEntity.getBookings().isEmpty()){
            roomDTO.setBookings(roomEntity.getBookings().stream().map(Utils::mapBookingEntityToDTO).toList());
        }
        return roomDTO;
    }
    public static BookingDTO mapBookingEntityToDTO(BookingEntity bookingEntity) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(bookingEntity.getId());
        bookingDTO.setCheckInDate(bookingEntity.getCheckInDate());
        bookingDTO.setCheckOutDate(bookingEntity.getCheckOutDate());
        bookingDTO.setNumOfAdults(bookingEntity.getNumOfAdults());
        bookingDTO.setNumOfChildren(bookingEntity.getNumOfChildren());
        bookingDTO.setTotalNumOfGuests(bookingEntity.getTotalNumOfGuests());
        bookingDTO.setBookingConfirmationCode(bookingEntity.getBookingConfirmationCode());
        bookingDTO.setUser(mapUserEntityToDTO(bookingEntity.getUser()));
        bookingDTO.setRoom(mapRoomEntityToDTO(bookingEntity.getRoom()));
        return bookingDTO;
    }

    public static List<UserDTO> mapUserEntityToDTOList(List<UserEntity> userEntityList) {
        return userEntityList.stream().map(Utils::mapUserEntityToDTO).toList();
    }
    public static List<RoomDTO> mapDTOToRoomEntityList(List<RoomEntity> roomEntityList) {
        return roomEntityList.stream().map(Utils::mapRoomEntityToDTO).toList();
    }
    public static List<BookingDTO> mapDTOToBookingEntityList(List<BookingEntity> bookingEntityList) {
        return bookingEntityList.stream().map(Utils::mapBookingEntityToDTO).toList();
    }
}
