package com.phai.customerinfoservice.services;

import com.phai.customerinfoservice.exception.AppException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * Uploads a file and returns the URL
     * @param file The file to upload
     * @param directory The directory to store the file in
     * @return The URL of the uploaded file
     * @throws AppException if the upload fails
     */
    String uploadFile(MultipartFile file, String directory) throws AppException;

    /**
     * Deletes a file by URL
     * @param fileUrl The URL of the file to delete
     * @throws AppException if the deletion fails
     */
    void deleteFile(String fileUrl) throws AppException;
}
