package ru.savinov.pizzaservice.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@Service
public class ImageService {

    @Value("${app.upload.bucket}")
    private String pathToBucket;
    @Value("${app.upload.buffer-size}")
    private Integer bufferSize;

    @SneakyThrows
    public void writeImage(MultipartFile image) {
        InputStream imageContent = image.getInputStream();
        String imageName = image.getOriginalFilename();
        var imageFullPath = Path.of(pathToBucket, imageName);
        Files.createDirectories(imageFullPath.getParent());
        try (imageContent; var outputStream = Files.newOutputStream(imageFullPath, CREATE, TRUNCATE_EXISTING)) {
            byte[] buffer = new byte[bufferSize];
            int bytesRead;
            while ((bytesRead = imageContent.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

}
