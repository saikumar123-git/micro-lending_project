package com.example.micro_lending.Project.Repository;

import com.example.micro_lending.Project.Entity.DocumentUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentUploadRepository extends JpaRepository<DocumentUpload,Long> {
}
