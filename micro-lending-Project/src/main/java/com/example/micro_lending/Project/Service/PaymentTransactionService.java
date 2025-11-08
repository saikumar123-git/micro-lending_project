package com.example.micro_lending.Project.Service;

import com.example.micro_lending.Project.Entity.*;
import com.example.micro_lending.Project.Repository.PaymentTransactionRepository;
import com.example.micro_lending.Project.Repository.RepaymentScheduleRepository;
import com.example.micro_lending.Project.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentTransactionService {

    private final PaymentTransactionRepository paymentTransactionRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public PaymentTransactionService(PaymentTransactionRepository paymentTransactionRepository,
                                     RepaymentScheduleRepository repaymentScheduleRepository,
                                     UserRepository userRepository,
                                     EmailService emailService) {
        this.paymentTransactionRepository = paymentTransactionRepository;
        this.repaymentScheduleRepository = repaymentScheduleRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    // Make a payment
    public PaymentTransaction makePayment(Long userId, Long scheduleId, BigDecimal amount,
                                          String method, String gatewayRef) throws jakarta.mail.MessagingException{

        User payer = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        RepaymentSchedule schedule = repaymentScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Repayment schedule not found"));

        if (schedule.getPaymentStatus() == PaymentStatus.PAID) {
            throw new RuntimeException("This installment is already paid.");
        }

        // Create transaction
        PaymentTransaction tx = PaymentTransaction.builder()
                .payer(payer)
                .repaymentSchedule(schedule)
                .amount(amount)
                .paymentMethod(method)
                .gatewayReference(gatewayRef)
                .transactionTime(LocalDateTime.now())
                .status(TransactionStatus.SUCCESS)
                .build();

        // Update schedule
        schedule.setPaymentStatus(PaymentStatus.PAID);
        schedule.setPaidAt(LocalDateTime.now());
        schedule.setPaymentTransaction(tx);

        repaymentScheduleRepository.save(schedule);
        PaymentTransaction savedTx = paymentTransactionRepository.save(tx);

        // Send confirmation email
        emailService.sendEmail(
                payer.getEmail(),
                "Payment Received",
                emailService.buildPaymentEmailBody(savedTx)
        );

        return savedTx;
    }
}
