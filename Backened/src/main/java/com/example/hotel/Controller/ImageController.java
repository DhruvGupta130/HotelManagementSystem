package com.example.hotel.Controller;

import com.example.hotel.Service.Utils.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/images/get")
    public ResponseEntity<Resource> download(@RequestParam int id) {
        try {
            Optional<Resource> optional = imageService.getImageResource(id);
            if(optional.isPresent()) {
                Resource resource = optional.get();
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
            }
            return ResponseEntity.status(404).build();
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
