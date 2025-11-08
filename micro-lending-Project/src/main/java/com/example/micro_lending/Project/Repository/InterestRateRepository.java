package com.example.micro_lending.Project.Repository;

import com.example.micro_lending.Project.Entity.InterestRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestRateRepository extends JpaRepository<InterestRate, Long> {
    List<InterestRate> findByProductCode(String productCode);
}
