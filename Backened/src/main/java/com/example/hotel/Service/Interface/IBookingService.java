package com.example.hotel.Service.Interface;

import com.example.hotel.DTO.Response;
import com.example.hotel.Entity.BookingEntity;

public interface IBookingService {

    Response bookRoom(long roomId, long userId, BookingEntity bookingEntity);
    Response findBookingsByConfirmationCode(String confirmationCode);
    Response getAllBookings();
    Response cancelBooking(long bookingId);
}
