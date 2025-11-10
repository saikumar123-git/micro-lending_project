package com.example.micro_lending.Project.Repository;

import com.example.micro_lending.Project.Entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByEntityName(String entityName);
    List<AuditLog> findByPerformedBy(String performedBy);
}
