package com.example.hotel.Service.Implementation;

import com.example.hotel.DTO.BookingDTO;
import com.example.hotel.DTO.EmailBody;
import com.example.hotel.DTO.Response;
import com.example.hotel.Entity.BookingEntity;
import com.example.hotel.Entity.RoomEntity;
import com.example.hotel.Entity.UserEntity;
import com.example.hotel.Exception.HotelException;
import com.example.hotel.Repository.BookingRepo;
import com.example.hotel.Repository.RoomRepo;
import com.example.hotel.Repository.UserRepo;
import com.example.hotel.Service.Interface.IBookingService;
import com.example.hotel.Service.Utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingService implements IBookingService {

    private final BookingRepo bookingRepo;
    private final RoomRepo roomRepo;
    private final UserRepo userRepo;
    private final EmailService emailService;


    @Override
    public Response bookRoom(long roomId, long userId, BookingEntity bookingEntity) {
        Response response = new Response();
        try {
            if (bookingEntity.getCheckInDate().isBefore(LocalDate.now())
            || bookingEntity.getCheckOutDate().isBefore(bookingEntity.getCheckInDate())) {
                throw new IllegalArgumentException("Please Choose the correct date");
            }
            RoomEntity room = roomRepo.findById(roomId).orElseThrow(() -> new HotelException("Room not found"));
            UserEntity user = userRepo.findById(userId).orElseThrow(() -> new HotelException("User not found"));
            List<BookingEntity> existingBookings = room.getBookings();
            if (!roomIsAvailable(bookingEntity, existingBookings)){
                throw new HotelException("Room is not available for selected date range");
            }
            bookingEntity.setRoom(room);
            bookingEntity.setUser(user);

            String bookingConfirmationCode = Utils.generateRandomString(15);
            bookingEntity.setBookingConfirmationCode(bookingConfirmationCode);

            BookingDTO bookingDTO = new BookingDTO();
            bookingDTO.setUser(Utils.mapUserEntityToDTO(user));
            bookingDTO.setRoom(Utils.mapRoomEntityToDTO(room));
            bookingDTO.setCheckInDate(bookingEntity.getCheckInDate());
            bookingDTO.setCheckOutDate(bookingEntity.getCheckOutDate());
            bookingDTO.setTotalNumOfGuests(bookingEntity.getTotalNumOfGuests());
            bookingDTO.setBookingConfirmationCode(bookingConfirmationCode);


            bookingRepo.save(bookingEntity);

            response.setStatusCode(200);
            response.setMessage("Booking successful");
            response.setBookingConfirmationCode(bookingConfirmationCode);

            EmailBody body = new EmailBody();
            emailService.sendConfirmation(user.getEmail(), "My Hotel Room Booking Confirmation", body.confirmationBody(bookingDTO));

        }catch (HotelException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Something went wrong "+e.getMessage());
        }
        return response;
    }

    @Override
    public Response findBookingsByConfirmationCode(String confirmationCode) {
        Response response = new Response();
        try {
            BookingEntity booking = bookingRepo.findByBookingConfirmationCode(confirmationCode)
                    .orElseThrow(() -> new HotelException("Booking not found"));
            BookingDTO bookingDTO = Utils.mapBookingEntityToDTO(booking);
            response.setStatusCode(200);
            response.setMessage("Booking retrieved successfully");
            response.setBooking(bookingDTO);
        }catch (Exception e){
            response.setStatusCode(400);
            response.setMessage("Something went wrong "+e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllBookings() {
        Response response = new Response();
        try {
            List<BookingEntity> bookings = bookingRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<BookingDTO> bookingDTOS = Utils.mapDTOToBookingEntityList(bookings);
            response.setStatusCode(200);
            response.setMessage("Bookings retrieved successfully");
            response.setBookingList(bookingDTOS);


        }catch (Exception e){
            response.setStatusCode(400);
            response.setMessage("Something went wrong "+e.getMessage());
        }
        return response;
    }

    @Override
    public Response cancelBooking(long bookingId) {
        Response response = new Response();
        try {
            BookingEntity booking = bookingRepo.findById(bookingId)
                    .orElseThrow(() -> new HotelException("Booking not found"));
            BookingDTO bookingDTO = Utils.mapBookingEntityToDTO(booking);
            bookingRepo.deleteById(bookingId);
            response.setStatusCode(200);
            response.setMessage("Booking cancelled successfully");
            EmailBody body = new EmailBody();
            emailService.sendCancellation(bookingDTO.getUser().getEmail(), "Important Update: Your Room Booking Has Been Cancelled", body.cancelBody(bookingDTO));
        }catch (Exception e){
            response.setStatusCode(400);
            response.setMessage("Something went wrong "+e.getMessage());
        }
        return response;
    }
    private boolean roomIsAvailable(BookingEntity bookingEntity, List<BookingEntity> existingBookings) {
        return existingBookings.stream()
                .noneMatch(bookings -> bookingEntity.getCheckInDate().equals(bookings.getCheckInDate())||
                        bookingEntity.getCheckOutDate().isBefore(bookings.getCheckOutDate())||
                        bookingEntity.getCheckInDate().isAfter(bookings.getCheckInDate())&&
                                bookingEntity.getCheckInDate().isBefore(bookings.getCheckOutDate())||
                        bookingEntity.getCheckInDate().isBefore(bookings.getCheckInDate())&&
                                bookingEntity.getCheckOutDate().equals(bookings.getCheckOutDate())||
                        bookingEntity.getCheckInDate().isBefore(bookings.getCheckInDate())&&
                                bookingEntity.getCheckOutDate().isAfter(bookings.getCheckOutDate())||
                        bookingEntity.getCheckInDate().equals(bookings.getCheckOutDate())&&
                                bookingEntity.getCheckOutDate().equals(bookings.getCheckInDate())||
                        bookingEntity.getCheckInDate().equals(bookings.getCheckOutDate())&&
                                bookingEntity.getCheckOutDate().equals(bookingEntity.getCheckInDate())
                );


    }
}
