package com.example.Venus.controller;

import com.example.Venus.base.BaseController;
import com.example.Venus.utils.ImageUtil;
import com.example.Venus.utils.SymmetricEncryptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Set;




/**
 * @author korash waiba
 * @version v1.0
 * @since 1/9/2025
 **/
@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageController extends BaseController {
    private  final SymmetricEncryptionUtil symmetricEncryptionUtil;
    private final ImageUtil imageUtil;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${encryption.key}")
    private String AES_KEY;

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/png", "image/jpeg", "image/jpg", "image/gif"
    );

    @GetMapping("/images")
    public ResponseEntity<?> serveFile(@RequestParam("encData") String encData) throws Exception {
        try {

            byte[] decodedBytes = Base64.getDecoder().decode(encData);

            String preparedEncData = new String(decodedBytes);
            String finalUploadDir = getDocumentImageDirectory(preparedEncData);
            Path filePath = Paths.get(finalUploadDir);

            System.out.println("IMAGE PATH: " + filePath.toString());

            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .contentType(MediaType.IMAGE_PNG)
                        .contentType(MediaType.IMAGE_GIF)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private String getDocumentImageDirectory(String encData) throws Exception {

        String decryptedData = symmetricEncryptionUtil.decrypt(encData,"QJEZeTLOjV7QRvM0YAQigdkGcBGymMl1gH15LlqXXks=", SymmetricEncryptionUtil.EncryptionAlgorithm.AES_CBC);
//        decryptedData.substring(0,decryptedData.lastIndexOf("\\"));

        return uploadDir + decryptedData;



    }
}
