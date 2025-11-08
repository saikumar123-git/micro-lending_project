package com.example.micro_lending.Project.Service;

import com.example.micro_lending.Project.Entity.InterestRate;
import com.example.micro_lending.Project.Repository.InterestRateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InterestRateService {

    private final InterestRateRepository interestRateRepository;

    public InterestRateService(InterestRateRepository interestRateRepository) {
        this.interestRateRepository = interestRateRepository;
    }

    // Create or update interest rate
    public InterestRate saveInterestRate(InterestRate rate) {
        return interestRateRepository.save(rate);
    }

    // Get all interest rates
    public List<InterestRate> getAllInterestRates() {
        return interestRateRepository.findAll();
    }

    // Get by ID
    public Optional<InterestRate> getInterestRateById(Long id) {
        return interestRateRepository.findById(id);
    }

    // Get by product code
    public List<InterestRate> getInterestRatesByProduct(String productCode) {
        return interestRateRepository.findByProductCode(productCode);
    }

    // Delete interest rate
    public void deleteInterestRate(Long id) {
        interestRateRepository.deleteById(id);
    }
}
