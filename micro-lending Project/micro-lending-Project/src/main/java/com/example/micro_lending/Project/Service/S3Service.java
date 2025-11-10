package com.example.micro_lending.Project.Service;

import com.example.micro_lending.Project.Entity.DocumentType;
import com.example.micro_lending.Project.Entity.DocumentUpload;
import com.example.micro_lending.Project.Entity.User;
import com.example.micro_lending.Project.Repository.DocumentUploadRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.io.IOException;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final DocumentUploadRepository documentUploadRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public S3Service(S3Client s3Client, DocumentUploadRepository documentUploadRepository) {
        this.s3Client = s3Client;
        this.documentUploadRepository = documentUploadRepository;
    }

    public void upload(MultipartFile file, User owner, DocumentType documentType) throws IOException {
        // Upload to S3
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(file.getOriginalFilename())
                        .contentType(file.getContentType())
                        .build(),
                RequestBody.fromBytes(file.getBytes())
        );

        // Save metadata to DB
        DocumentUpload doc = DocumentUpload.builder()
                .owner(owner)
                .documentType(documentType)
                .filename(file.getOriginalFilename())
                .contentType(file.getContentType())
                .sizeBytes(file.getSize())
                .storageUrl("s3://" + bucket + "/" + file.getOriginalFilename())
                .build();

        documentUploadRepository.save(doc);
    }

    public byte[] downloadFile(String filename) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(filename)
                .build();

        ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
        return objectBytes.asByteArray();
    }
}
