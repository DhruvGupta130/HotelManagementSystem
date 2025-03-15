package com.example.hotel.Repository;

import com.example.hotel.Entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepo extends JpaRepository<RoomEntity, Long> {
    @Query("SELECT DISTINCT r.roomType FROM RoomEntity r")
    List<String> findDistinctRoomTypes();

    @Query("SELECT r FROM RoomEntity r WHERE r.roomType LIKE %:roomType% AND r.id NOT IN (SELECT bk.room.id FROM BookingEntity bk WHERE (bk.checkInDate <= :checkOutDate) AND (bk.checkOutDate >= :checkInDate))")
    List<RoomEntity> findAvailableRoomsByDatesAndTypes(LocalDate checkInDate, LocalDate checkOutDate, String roomType);

    @Query("SELECT r FROM RoomEntity r WHERE r.id NOT IN (SELECT b.room.id FROM BookingEntity b)")
    List<RoomEntity> getAllAvailableRooms();
}
