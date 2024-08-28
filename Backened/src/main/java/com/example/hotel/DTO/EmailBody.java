package com.example.hotel.DTO;

import com.example.hotel.Entity.ForgotPass;

import java.time.format.DateTimeFormatter;

public class EmailBody {
    private static final String hotelName = "My Hotel";
    public String verifyBody(ForgotPass details) {
        String body = """
            Hi %s,
            Your OTP code is: %s.
            
            This code is valid for the next 5 minutes. For your security, please do not share this code with anyone.
            If you didn't request this, just ignore this email.
            
            Thanks,
            
            Support Team
            """;
        String name = details.getUser().getName();
        String otp = details.getToken();
        body = body.formatted(name, otp);
        return body;
    }
    public String confirmationBody(BookingDTO details) {
        String body = """
                Dear %s,
                
                We are delighted to inform you that your booking at %s has been successfully confirmed. Below are the details of your reservation:
                
                Booking Confirmation Code: %s
                
                Check-in Date: %s
                Check-out Date: %s
                Room Type: %s
                Number of Guests: %d
                
                We look forward to welcoming you to %s. For any questions or need further assistance, please do not hesitate to contact us at %s.
                
                Thank you for choosing %s. We wish you a pleasant stay!
                
                Best regards,
                
                %s
                """;
        return getString(details, body);
    }
    public String cancelBody(BookingDTO details) {
        String body = """
                Dear %s,
                
                We regret to inform you that your room booking at %s has been cancelled due to unforeseen circumstances. We sincerely apologize for any inconvenience this may cause.
                
                Booking Confirmation Code: %s
                
                Check-in Date: %s
                Check-out Date: %s
                Room Type: %s
                Number of Guests: %d
                
                We understand the importance of your reservation, and we deeply apologize for any inconvenience this cancellation may cause. If you have any questions or need further assistance, please feel free to contact our customer service team at %s.
                
                Once again, %s apologize for any inconvenience and appreciate your understanding.
                Thank you for choosing %s. We wish you a pleasant stay!
                
                Best regards,
                
                %s
                """;
        return getString(details, body);
    }

    private String getString(BookingDTO details, String body) {
        String name = details.getUser().getName();
        String code = details.getBookingConfirmationCode();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String checkInDate = details.getCheckInDate().format(formatter);
        String checkOutDate = details.getCheckOutDate().format(formatter);
        String roomType = details.getRoom().getRoomType();
        Integer numOfGuest = details.getTotalNumOfGuests();
        body = body.formatted(name, hotelName, code, checkInDate, checkOutDate, roomType, numOfGuest, hotelName, hotelName, hotelName, hotelName);
        return body;
    }
}
