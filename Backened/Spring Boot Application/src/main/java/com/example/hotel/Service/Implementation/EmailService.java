package com.example.hotel.Service.Implementation;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("verify-account@6981537.brevosend.com");
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
    public void sendConfirmation(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("Myhotel-confirmation@6981537.brevosend.com");
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
    public void sendCancellation(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("Myhotel-cancel@6981537.brevosend.com");
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
