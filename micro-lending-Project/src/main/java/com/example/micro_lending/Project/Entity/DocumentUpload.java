package com.example.micro_lending.Project.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.micro_lending.Project.Entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User owner;


    @Enumerated(EnumType.STRING)
    private DocumentType documentType;


    @NotBlank
    private String filename;


    @NotBlank
    private String contentType;


    private Long sizeBytes;


    private String storageUrl;


    private LocalDateTime uploadedAt = LocalDateTime.now();
}


