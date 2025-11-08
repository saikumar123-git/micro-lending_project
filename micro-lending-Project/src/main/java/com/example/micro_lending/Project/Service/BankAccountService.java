package com.example.micro_lending.Project.Service;

import com.example.micro_lending.Project.Entity.BankAccount;
import com.example.micro_lending.Project.Entity.User;
import com.example.micro_lending.Project.Repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {

    @Autowired
    private AuditLogService auditLogService;
    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    // Create or add a bank account
    public BankAccount addBankAccount(BankAccount account) {
        account.setVerified(false); // default not verified
        return bankAccountRepository.save(account);
    }

    // Get all bank accounts
    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

    // Get bank account by ID
    public Optional<BankAccount> getBankAccountById(Long id) {
        return bankAccountRepository.findById(id);
    }

    // Verify a bank account (admin action)
    public BankAccount verifyBankAccount(Long id,String adminUsername) {
        BankAccount account = bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));
        account.setVerified(true);
        BankAccount saved = bankAccountRepository.save(account);

        // ✅ Create audit log
        auditLogService.createLog(
                "BankAccount",
                saved.getId(),
                "REVIEW",
                adminUsername,
                "Bank account verified by admin"
        );

        return saved;
    }

    // Get accounts by user
    public List<BankAccount> getAccountsByUser(User user) {
        return bankAccountRepository.findByUser(user);
    }
}
