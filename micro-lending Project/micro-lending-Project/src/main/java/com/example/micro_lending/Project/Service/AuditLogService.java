package com.example.micro_lending.Project.Service;

import com.example.micro_lending.Project.Entity.AuditLog;
import com.example.micro_lending.Project.Repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public AuditLog createLog(String entityName, Long entityId, String action,
                              String performedBy, String details) {
        AuditLog log = AuditLog.builder()
                .entityName(entityName)
                .entityId(entityId)
                .action(action)
                .performedBy(performedBy)
                .details(details)
                .build();
        return auditLogRepository.save(log);
    }

    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }

    public List<AuditLog> getLogsByEntity(String entityName) {
        return auditLogRepository.findByEntityName(entityName);
    }

    public List<AuditLog> getLogsByUser(String username) {
        return auditLogRepository.findByPerformedBy(username);
    }
}
