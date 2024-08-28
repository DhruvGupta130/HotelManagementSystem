package com.example.hotel.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "hotel_images")
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String path;
    private String fileName;
    private String fileType;
}
