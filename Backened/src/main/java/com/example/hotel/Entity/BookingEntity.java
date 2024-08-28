package com.example.hotel.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.aspectj.lang.annotation.After;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "bookings")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @FutureOrPresent(message = "Please select the appropriate date")
    private LocalDate checkInDate;
    @FutureOrPresent(message = "Please select the appropriate date")
    private LocalDate checkOutDate;
    private int numOfAdults;
    private int numOfChildren;
    private int totalNumOfGuests;
    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    public void totalNumOfGuests() {
        this.totalNumOfGuests = this.numOfAdults + this.numOfChildren;
    }
    public void setNumOfAdults(@Min(value = 1, message = "Number of Adults cannot be 0") int numOfAdults) {
        this.numOfAdults = numOfAdults;
        totalNumOfGuests();
    }
    public void setNumOfChildren(@Min(value = 0, message = "Number of Children cannot be negative") int numOfChildren) {
        this.numOfChildren = numOfChildren;
        totalNumOfGuests();
    }

    @Override
    public String toString() {
        return "BookingEntity{" +
                "id=" + id +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numOfAdults=" + numOfAdults +
                ", numOfChildren=" + numOfChildren +
                ", totalNumOfGuests=" + totalNumOfGuests +
                ", bookingConfirmationCode='" + bookingConfirmationCode + '\'' +
                '}';
    }
}
