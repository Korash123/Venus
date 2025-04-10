package com.example.Venus.utils;


import com.example.Venus.contants.APP_CONSTANTS;
import com.example.Venus.exception.ImageProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

/*
    @created 16/05/2024 11:38 PM
    @project iam
    @author korash.waiba
*/
@Slf4j
@Component
@RequiredArgsConstructor
public class ImageUtil {
    private final MessageSourceUtils messageSourceUtils;

    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * Saves a MultipartFile to a location on the Linux hard disk.
     *
     * @param file The MultipartFile to save
     * @return The full path of the saved file
     * @throws IOException If an I/O error occurs
     */
    public String saveImage(MultipartFile file, String directorySuffix) throws IOException {
//        if(file.isEmpty()){
//            throw new GenericException(APP_CONSTANTS.BAD_REQUEST, messageSourceUtils.getMessage("required.message", new Object[]{"Image attachment"}),
//                    AgetMethodName(), null, APP_CONSTANTS.SERVICE_NAME);
////            throw new BadRequestException("Attachment cannot be empty.", HttpStatus.BAD_REQUEST.value());
//        }

        if (file.isEmpty()) {
            String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
            throw new ImageProcessingException(
                    messageSourceUtils.getMessage("required.message", new Object[]{"Image attachment"}),
                    APP_CONSTANTS.BAD_REQUEST,
                    methodName,
                    APP_CONSTANTS.SERVICE_NAME
            );
        }

        String finalUploadDir = uploadDir + directorySuffix;
        // Create the upload directory if it doesn't exist
        File directory = new File(finalUploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String fileName = "";
        if(!file.isEmpty() && file.getOriginalFilename()!=null){
            log.info("File found. Extracting the filename. ");
            fileName =  file.getOriginalFilename().replace(" ", "_");
        }else{
            log.info("File not found ");
        }
        // Generate a unique filename
        String uniqueFileName = UUID.randomUUID().toString() + "_" +fileName ;

        // Create the full path
        Path filepath = Paths.get(finalUploadDir, uniqueFileName);

        // Save the file
        Files.copy(file.getInputStream(), filepath);

//        return filepath.toString();
        return Paths.get(directorySuffix, uniqueFileName).toString();

    }

    /**
     * Generates a URI for an image given its full path.
     *
     * @param fullPath The full path of the image on the disk
     * @return A URI that, when accessed, will display the image
     */
    public String getImageUri(String fullPath, String params) {
        // Extract the filename from the full path
        //TODO: Base64 encode the params:
        String encodedParams = Base64.getEncoder().encodeToString(params.getBytes());

        // Generate the URI
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/images")

                .queryParam("encData",encodedParams )
                .toUriString();
    }

    public String getImageUri(String fullPath, String params, String encUserId) {
        // Extract the filename from the full path
        String filename = Paths.get(fullPath).getFileName().toString() ;

        // Generate the URI
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/images/")
                .path(filename)
                .queryParam("type",params )
                .queryParam("data", encUserId)
                .toUriString();
    }
    public String saveImageWithCustomName(MultipartFile file, Path customFilePath) throws IOException {
        // Ensure the directory exists
        Files.createDirectories(customFilePath.getParent());

        // Save the file with the custom name
        Path fullFilePath = customFilePath.toAbsolutePath();
        Files.write(fullFilePath, file.getBytes());

        // Return the file URL or relative path for storage in the database
        return fullFilePath.toString();
    }

    public void deleteFile(String filePath) {
        String finalUploadDir = uploadDir + filePath;

        File file = new File(finalUploadDir);

        // Check if the file exists
        if (file.exists()) {
            // Attempt to delete the file
            if (file.delete()) {
                System.out.println("File deleted successfully: " + filePath);
            } else {
                System.out.println("Failed to delete the file: " + filePath);
            }
        } else {
            System.out.println("File not found: " + filePath);
        }
    }
}