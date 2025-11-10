package com.example.micro_lending.Project.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(optional = false)
    @JoinColumn(name = "payer_id")
    @JsonIgnore
    private User payer;


    @OneToOne
    @JoinColumn(name = "schedule_id")
    @JsonIgnore
    private RepaymentSchedule repaymentSchedule;


    private BigDecimal amount;


    private LocalDateTime transactionTime = LocalDateTime.now();


    private String paymentMethod; // e.g. UPI, NETBANKING, WALLET, CASH


    private String gatewayReference; // external payment provider ref


    @Enumerated(EnumType.STRING)
    private TransactionStatus status = TransactionStatus.SUCCESS;
}


