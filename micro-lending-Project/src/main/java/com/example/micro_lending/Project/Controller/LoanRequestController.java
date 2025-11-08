package com.example.micro_lending.Project.Controller;

import com.example.micro_lending.Project.Entity.LoanRequest;
import com.example.micro_lending.Project.Entity.LoanStatus;
import com.example.micro_lending.Project.Service.LoanRequestService;
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
    public ResponseEntity<LoanRequest> createLoanRequest(@RequestBody LoanRequest loanRequest) {
        LoanRequest savedLoan = loanRequestService.createLoanRequest(loanRequest);
        return ResponseEntity.ok(savedLoan);
    }

    @GetMapping
    public ResponseEntity<List<LoanRequest>> getAllLoanRequests() {
        return ResponseEntity.ok(loanRequestService.getAllLoanRequests());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<LoanRequest>> getById(@PathVariable Long id){
        return ResponseEntity.ok(loanRequestService.getUserById(id));
    }
}

