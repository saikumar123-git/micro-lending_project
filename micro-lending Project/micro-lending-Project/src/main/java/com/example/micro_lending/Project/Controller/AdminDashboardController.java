package com.example.micro_lending.Project.Controller;

import com.example.micro_lending.Project.Entity.BankAccount;
import com.example.micro_lending.Project.Entity.LoanAgreement;
import com.example.micro_lending.Project.Entity.LoanRequest;
import com.example.micro_lending.Project.Entity.LoanStatus;
import com.example.micro_lending.Project.Service.BankAccountService;
import com.example.micro_lending.Project.Service.LoanAgreementService;
import com.example.micro_lending.Project.Service.LoanRequestService;
import com.example.micro_lending.Project.Service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {
    private final BankAccountService bankAccountService;
    private final LoanRequestService loanRequestService;
    private final LoanAgreementService loanAgreementService;
    private final S3Service s3Service;

    public AdminDashboardController(LoanRequestService loanRequestService,
                                    LoanAgreementService loanAgreementService,
                                    S3Service s3Service,BankAccountService bankAccountService) {
        this.loanRequestService = loanRequestService;
        this.loanAgreementService = loanAgreementService;
        this.s3Service = s3Service;
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/loan-requests")
    public ResponseEntity<List<LoanRequest>> getAllLoanRequests() {
        return ResponseEntity.ok(loanRequestService.getAllLoanRequests());
    }

    @GetMapping("/loan-requests/{id}")
    public ResponseEntity<LoanRequest> getLoanRequestById(@PathVariable Long id) {
        return loanRequestService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/loan-requests/{id}/status")
    public ResponseEntity<LoanRequest> updateLoanStatus(@PathVariable Long id,
                                                        @RequestParam LoanStatus status,  @RequestParam String adminUsername )throws jakarta.mail.MessagingException {
        return ResponseEntity.ok(loanRequestService.updateLoanRequestStatus(id, status,adminUsername));
    }

    @GetMapping("/loan-agreements")
    public ResponseEntity<List<LoanAgreement>> getAllLoanAgreements() {
        return ResponseEntity.ok(loanAgreementService.getAllLoanAgreements());
    }

    @GetMapping("/documents/{filename}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable String filename) {
        byte[] data = s3Service.downloadFile(filename);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                .body(data);
    }
    @GetMapping("/bank-accounts")
    public ResponseEntity<List<BankAccount>> getAllBankAccounts() {
        return ResponseEntity.ok(bankAccountService.getAllBankAccounts());
    }

    @PutMapping("/bank-accounts/{id}/verify")
    public ResponseEntity<BankAccount> verifyBankAccount(@PathVariable Long id,  @RequestParam String adminUsername) {
        return ResponseEntity.ok(bankAccountService.verifyBankAccount(id,adminUsername));
    }
}
