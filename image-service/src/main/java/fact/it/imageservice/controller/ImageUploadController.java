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
            return new ResponseEntity<>(imageUploadService.uploadImage(file), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error uploading image", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InvalidContentTypeException e) {
            return new ResponseEntity<>("Invalid image type. Only png, jpg, avif and webp are allowed",
                    HttpStatus.BAD_REQUEST);
        }
    }
}
