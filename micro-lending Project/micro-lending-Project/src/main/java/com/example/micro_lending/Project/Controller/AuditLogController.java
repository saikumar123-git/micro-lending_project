package com.example.micro_lending.Project.Controller;

import com.example.micro_lending.Project.Entity.AuditLog;
import com.example.micro_lending.Project.Service.AuditLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/audit-logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }


    @GetMapping
    public ResponseEntity<List<AuditLog>> getAllLogs() {
        return ResponseEntity.ok(auditLogService.getAllLogs());
    }

    @GetMapping("/entity/{entityName}")
    public ResponseEntity<List<AuditLog>> getLogsByEntity(@PathVariable String entityName) {
        return ResponseEntity.ok(auditLogService.getLogsByEntity(entityName));
    }
    @GetMapping("/user/{username}")
    public ResponseEntity<List<AuditLog>> getLogsByUser(@PathVariable String username) {
        return ResponseEntity.ok(auditLogService.getLogsByUser(username));
    }
    @PostMapping
    public ResponseEntity<AuditLog> createLog(@RequestBody AuditLog log) {
        return ResponseEntity.ok(auditLogService.createLog(
                log.getEntityName(),
                log.getEntityId(),
                log.getAction(),
                log.getPerformedBy(),
                log.getDetails()
        ));
    }
}
