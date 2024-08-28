package com.example.hotel.Service.Utils;

import com.example.hotel.Entity.ImageEntity;
import com.example.hotel.Exception.HotelException;
import com.example.hotel.Repository.ImageRepo;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepo imageRepo;
    public final String dir = "hotel_images";

    public String saveImage(MultipartFile file) throws IOException {
        String ip = "localhost";
        try {
            ImageEntity image = new ImageEntity();
            Path imageDir = Path.of(dir);
            if(!Files.exists(imageDir))
                Files.createDirectories(imageDir);
            Path imagePath = Path.of(dir, file.getOriginalFilename());
            image.setPath(imagePath.toString());
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            Files.write(imagePath, file.getBytes());
            imageRepo.save(image);
            return "http://"+ ip +":8080"+"/images/get"+"?id="+image.getId();
        }catch (MalformedURLException e) {
            throw new HotelException("Error: "+e.getMessage());
        }
    }
    public Optional<Resource> getImageResource(int id) throws MalformedURLException {
        Optional<ImageEntity> image = imageRepo.findById(id);
        if (image.isPresent()) {
            Resource resource = new UrlResource(Path.of(image.get().getPath()).toUri());
            if(resource.exists()){
                return Optional.of(resource);
            }
            imageRepo.deleteById(id);
        }
        return Optional.empty();
    }
}
