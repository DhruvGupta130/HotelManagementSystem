package com.example.hotel.Repository;

import com.example.hotel.Entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepo extends JpaRepository<BookingEntity, Long> {

    List<BookingEntity> findByRoomId(Long id);
    Optional<BookingEntity> findByBookingConfirmationCode(String confirmationCode);
    List<BookingEntity> findByUserId(Long userId);
}
