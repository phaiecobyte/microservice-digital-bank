package com.phai.customerservice.services.impl;

import com.phai.customerservice.exception.AppException;
import com.phai.customerservice.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Value("${app.file.upload-dir:uploads}")
    private String uploadDir;

    @Value("${app.file.base-url:http://localhost:9000/api/files}")
    private String baseUrl;

    @Override
    public String uploadFile(MultipartFile file, String directory) throws AppException {
        try {
            // Validate file
            if (file.isEmpty()) {
                throw new AppException(HttpStatus.BAD_REQUEST, "FILE_EMPTY", "Please select a file to upload");
            }

            // Validate file type
            String fileType = file.getContentType();
            if (fileType == null || !fileType.startsWith("image/")) {
                throw new AppException(HttpStatus.BAD_REQUEST, "INVALID_FILE_TYPE",
                        "Only image files are allowed");
            }

            // Get file extension
            String fileName = file.getOriginalFilename();
            String extension = "";
            if (fileName != null && fileName.contains(".")) {
                extension = fileName.substring(fileName.lastIndexOf('.'));
            }

            // Create unique filename
            String uniqueFileName = UUID.randomUUID().toString() + extension;

            // Create directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir, directory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save file
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            log.info("File uploaded successfully: {}", filePath);

            // Return relative URL
            return baseUrl + "/" + directory + "/" + uniqueFileName;

        } catch (IOException e) {
            log.error("Failed to upload file", e);
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "UPLOAD_FAILED",
                    "Failed to upload file: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteFile(String fileUrl) throws AppException {
        try {
            if (fileUrl == null || fileUrl.isBlank()) {
                return;
            }

            // Extract filename from URL
            String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
            String directory = fileUrl.substring(baseUrl.length() + 1, fileUrl.lastIndexOf('/'));

            // Construct file path
            Path filePath = Paths.get(uploadDir, directory, fileName);

            // Delete file if it exists
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                log.info("File deleted successfully: {}", filePath);
            } else {
                log.warn("File not found for deletion: {}", filePath);
            }

        } catch (IOException e) {
            log.error("Failed to delete file", e);
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "DELETE_FAILED",
                    "Failed to delete file: " + e.getMessage(), e);
        }
    }
}
