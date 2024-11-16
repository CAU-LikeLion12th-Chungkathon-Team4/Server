package com.chungkathon.squirrel.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName = "chungkathon"; // S3 버킷 이름

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadPhoto(MultipartFile file) {
        String fileName = generateFileName(file.getOriginalFilename());

        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );

            // 업로드된 파일의 URL 반환
            return "https://" + bucketName + ".s3.ap-northeast-2.amazonaws.com/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }

    private String generateFileName(String originalFileName) {
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID() + extension;
    }
}
