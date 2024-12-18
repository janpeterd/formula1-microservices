package fact.it.imageservice.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import fact.it.imageservice.exception.InvalidContentTypeException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageUploadService {
    public String uploadImage(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get("target/classes/static/images/");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        System.out.println("File Content Type: " + file.getContentType());
        ArrayList<String> allowedTypes = new ArrayList<String>(
                List.of("image/jpeg", "image/png", "image/webp", "image/octet-stream"));

        if (allowedTypes.contains(file.getContentType())) {

            String fileName = file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Path relativePath = filePath.subpath(3, filePath.getNameCount()); // you can use this part of the string to
                                                                              // get
                                                                              // the image

            return relativePath.toString();
        }
        System.out.println("IMG FILETYPE NOT ALLOWED");
        throw new InvalidContentTypeException(
                "Invalid file type. Allowed types are: image/jpeg, image/png, image/webp.");

    }
}
