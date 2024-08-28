package com.example.hotel.Repository;

import com.example.hotel.Entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepo extends JpaRepository<ImageEntity, Integer>{
    Optional<ImageEntity> findByFileName(String fileName);
}
