package com.example.micro_lending.Project.Controller;

import com.example.micro_lending.Project.Entity.LoanAgreement;

import com.example.micro_lending.Project.Service.LoanAgreementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan-agreements")
public class LoanAgreementController {

    private final LoanAgreementService loanAgreementService;

    public LoanAgreementController(LoanAgreementService loanAgreementService) {
        this.loanAgreementService = loanAgreementService;
    }

    @GetMapping
    public ResponseEntity<List<LoanAgreement>> getAllLoanAgreements() {
        return ResponseEntity.ok(loanAgreementService.getAllLoanAgreements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanAgreement> getLoanAgreementById(@PathVariable Long id) {
        return loanAgreementService.getLoanAgreementById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
