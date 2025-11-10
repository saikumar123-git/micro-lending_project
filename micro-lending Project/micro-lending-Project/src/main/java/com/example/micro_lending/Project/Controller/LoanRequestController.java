package com.example.micro_lending.Project.Controller;

import com.example.micro_lending.Project.Entity.LoanRequest;
import com.example.micro_lending.Project.Entity.LoanStatus;
import com.example.micro_lending.Project.Service.LoanRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/loan-requests")
public class LoanRequestController {
    private final LoanRequestService loanRequestService;

    public LoanRequestController(LoanRequestService loanRequestService){
        this.loanRequestService=loanRequestService;
    }

    @PostMapping
    public ResponseEntity<?> createLoanRequest(@RequestBody LoanRequest loanRequest) {
        try {
            LoanRequest savedLoan = loanRequestService.createLoanRequest(loanRequest);
            return new ResponseEntity<>(savedLoan, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<LoanRequest>> getAllLoanRequests() {
        return ResponseEntity.ok(loanRequestService.getAllLoanRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanRequest> getById(@PathVariable Long id){
        return loanRequestService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<LoanRequest> updateLoanStatus(
            @PathVariable Long id,
            @RequestParam LoanStatus status,
            @RequestHeader(name = "X-Admin-Username", required = true) String adminUsername)
            throws Exception
    {
        try {
            LoanRequest updatedLoan = loanRequestService.updateLoanRequestStatus(id, status, adminUsername);
            return ResponseEntity.ok(updatedLoan);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}