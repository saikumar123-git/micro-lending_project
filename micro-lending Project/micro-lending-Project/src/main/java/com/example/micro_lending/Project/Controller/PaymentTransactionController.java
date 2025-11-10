package com.example.micro_lending.Project.Controller;

import com.example.micro_lending.Project.Entity.PaymentTransaction;
import com.example.micro_lending.Project.Service.PaymentTransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/payments")
public class PaymentTransactionController {

    private final PaymentTransactionService paymentTransactionService;

    public PaymentTransactionController(PaymentTransactionService paymentTransactionService) {
        this.paymentTransactionService = paymentTransactionService;
    }

    @PostMapping("/make")
    public ResponseEntity<PaymentTransaction> makePayment(
            @RequestParam Long userId,
            @RequestParam Long scheduleId,
            @RequestParam BigDecimal amount,
            @RequestParam String method,
            @RequestParam(required = false) String gatewayRef
    ) throws jakarta.mail.MessagingException{
        PaymentTransaction tx = paymentTransactionService.makePayment(userId, scheduleId, amount, method, gatewayRef);
        return ResponseEntity.ok(tx);
    }
}
