package com.example.micro_lending.Project.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "interest_rates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterestRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String productCode; // e.g. PERSONAL_MICRO_LOAN
    private BigDecimal annualRate;
    private LocalDate validFrom;
    private LocalDate validTo;
}