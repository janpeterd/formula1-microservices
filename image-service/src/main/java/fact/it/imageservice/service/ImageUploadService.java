package fact.it.imageservice.service;

import java.io.IOException;
import java.io.InputStream;
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
        // NOTE: I know this is not best practice, but in production this entire
        // service would be replaced with a CDN or S3 bucket
        Path uploadPath = Paths.get("target/classes/static/images/");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        ArrayList<String> allowedTypes = new ArrayList<String>(
                List.of("image/jpeg", "image/png", "image/webp", "image/avif", "application/octet-stream"));

        System.out.println("CONTENT TYPE IMAGE UPLOAD: " + file.getContentType());
        if (allowedTypes.contains(file.getContentType())) {

            String fileName = file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            try (InputStream in = file.getInputStream()) {
                Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Wrote file to disk");
            } catch (IOException ioException) {
                System.out.println("Error write file to disk: " + ioException);
            } catch (UnsupportedOperationException unsupportedOperationException) {
                System.out.println("Error write file to disk UNSUPPORTED: " + unsupportedOperationException);
            } catch (SecurityException securityException) {
                System.out.println("Error not enough permissions " + securityException);
            }

            Path relativePath = filePath.subpath(3, filePath.getNameCount());

            return relativePath.toString();
        }
        throw new InvalidContentTypeException(
                "Invalid file type. Allowed types are: image/jpeg, image/png, image/webp, image/avif, image/octet-stream.");

    }
}
