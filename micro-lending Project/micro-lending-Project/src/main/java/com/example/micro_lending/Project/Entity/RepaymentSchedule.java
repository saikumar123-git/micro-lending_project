package com.example.micro_lending.Project.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "repayment_schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepaymentSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "loan_agreement_id")
    @JsonIgnore
    private LoanAgreement loanAgreement;
    private Integer installmentNumber;
    private LocalDate dueDate;
    private BigDecimal principalComponent;
    private BigDecimal interestComponent;
    private BigDecimal totalDue;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    private LocalDateTime paidAt;

    @OneToOne(mappedBy = "repaymentSchedule", cascade = CascadeType.ALL)
    private PaymentTransaction paymentTransaction;
}