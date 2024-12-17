package fact.it.imageservice.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fact.it.imageservice.exception.InvalidContentTypeException;
import fact.it.imageservice.service.ImageUploadService;
import lombok.RequiredArgsConstructor;

@Controller
@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class ImageUploadController {
    private final ImageUploadService imageUploadService;

    @PostMapping
    public ResponseEntity<String> createGp(@RequestParam MultipartFile file) {
        try {
            String filePath = imageUploadService.uploadImage(file);
            return ResponseEntity.ok(filePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        } catch (InvalidContentTypeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Invalid image type. Only png, jpg and webp are allowed");
        }
    }
}
