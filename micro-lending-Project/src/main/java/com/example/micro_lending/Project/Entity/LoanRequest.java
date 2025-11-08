package com.example.micro_lending.Project.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.micro_lending.Project.Entity.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "applicant_id",nullable = false)
    @JsonIgnore
    private User applicant;

    @NotNull
    private BigDecimal principalAmount;

    @NotNull
    private Integer tenorMonths;

    @NotNull
   private BigDecimal requestedInterestRate;

    @NotNull
    private String productCode;

    @Enumerated(EnumType.STRING)
    private LoanStatus status = LoanStatus.PENDING;

    private String purpose;

    private LocalDateTime appliedAt;

    private LocalDateTime reviewedAt;

    private String reviewerComments;

    @OneToOne(mappedBy = "loanRequest", cascade = CascadeType.ALL)
    private LoanAgreement agreement;

    @PrePersist
    protected void onCreate() {
        appliedAt = LocalDateTime.now();
    }
}
