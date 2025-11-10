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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "loan_agreements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanAgreement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "loan_request_id")
    @JsonIgnore
    private LoanRequest loanRequest;
    private BigDecimal approvedPrincipal;
    private BigDecimal annualInterestRate;
    private Integer tenorMonths;
    private LocalDate disbursementDate;
    private LocalDate maturityDate;

    @OneToMany(mappedBy = "loanAgreement", cascade = CascadeType.ALL)
    private List<RepaymentSchedule> schedules = new ArrayList<>();
    private LocalDateTime createdAt = LocalDateTime.now();
}