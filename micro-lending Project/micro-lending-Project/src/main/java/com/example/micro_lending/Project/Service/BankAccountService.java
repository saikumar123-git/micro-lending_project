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

    public BankAccount addBankAccount(BankAccount account) {
        account.setVerified(false);
        return bankAccountRepository.save(account);
    }

    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public Optional<BankAccount> getBankAccountById(Long id) {
        return bankAccountRepository.findById(id);
    }

    public BankAccount verifyBankAccount(Long id,String adminUsername) {
        BankAccount account = bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));
        account.setVerified(true);
        BankAccount saved = bankAccountRepository.save(account);

        auditLogService.createLog(
                "BankAccount",
                saved.getId(),
                "REVIEW",
                adminUsername,
                "Bank account verified by admin"
        );

        return saved;
    }

    public List<BankAccount> getAccountsByUser(User user) {
        return bankAccountRepository.findByUser(user);
    }
}
