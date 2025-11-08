package com.example.micro_lending.Project.Controller;

import com.example.micro_lending.Project.Entity.BankAccount;
import com.example.micro_lending.Project.Entity.User;
import com.example.micro_lending.Project.Service.BankAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank-accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    // Add a new bank account
    @PostMapping
    public ResponseEntity<BankAccount> addBankAccount(@RequestBody BankAccount account) {
        return ResponseEntity.ok(bankAccountService.addBankAccount(account));
    }

    // Get all bank accounts (admin)
    @GetMapping
    public ResponseEntity<List<BankAccount>> getAllAccounts() {
        return ResponseEntity.ok(bankAccountService.getAllBankAccounts());
    }

    // Get bank account by ID
    @GetMapping("/{id}")
    public ResponseEntity<BankAccount> getAccountById(@PathVariable Long id) {
        return bankAccountService.getBankAccountById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // Get bank accounts for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BankAccount>> getAccountsByUser(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        return ResponseEntity.ok(bankAccountService.getAccountsByUser(user));
    }
}
