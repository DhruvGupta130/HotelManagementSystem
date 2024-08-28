package com.example.hotel.Service.Interface;

import com.example.hotel.DTO.LoginRequest;
import com.example.hotel.DTO.Response;
import com.example.hotel.Entity.UserEntity;

public interface IUserService {
    Response login(LoginRequest loginRequest);

    Response register(UserEntity userEntity);

    Response getAllUsers();

    Response getUserBookingHistory(long userId);

    Response deleteUser(long userId);

    Response updateUser(Long id, UserEntity userEntity);


    Response getUserById(long userId);

    Response getUserInfo(String email);
}
