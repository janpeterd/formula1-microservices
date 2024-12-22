package fact.it.imageservice.controller;

import fact.it.imageservice.exception.InvalidContentTypeException;
import fact.it.imageservice.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageUploadController {
    private final ImageUploadService imageUploadService;

    @GetMapping("/{filename:.+}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        try {
            Path path = Paths.get("target/classes/static/images/" + filename);
            byte[] image = Files.readAllBytes(path);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", Files.probeContentType(path));
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile file) {
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
