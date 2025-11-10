package com.example.micro_lending.Project.Controller;

import com.example.micro_lending.Project.Entity.DocumentType;
import com.example.micro_lending.Project.Entity.User;
import com.example.micro_lending.Project.Service.S3Service;
import com.example.micro_lending.Project.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class S3Controller {

    private final S3Service s3Service;
    private final UserService userService;

    public S3Controller(S3Service s3Service, UserService userService) {
        this.s3Service = s3Service;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("documentType") DocumentType documentType,
            @RequestParam("userId") Long userId) throws IOException {

        User owner = userService.getUserById(userId);
        s3Service.upload(file, owner, documentType);
        return ResponseEntity.ok("File successfully uploaded: " + file.getOriginalFilename());
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<byte[]> download(@PathVariable String filename) {
        byte[] data = s3Service.downloadFile(filename);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                .body(data);
    }
}
