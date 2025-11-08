package com.example.micro_lending.Project.Controller;

import com.example.micro_lending.Project.Entity.InterestRate;
import com.example.micro_lending.Project.Service.InterestRateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interest-rates")
public class InterestRateController {

    private final InterestRateService interestRateService;

    public InterestRateController(InterestRateService interestRateService) {
        this.interestRateService = interestRateService;
    }

    @PostMapping
    public ResponseEntity<InterestRate> createInterestRate(@RequestBody InterestRate rate) {
        return ResponseEntity.ok(interestRateService.saveInterestRate(rate));
    }

    @GetMapping
    public ResponseEntity<List<InterestRate>> getAllInterestRates() {
        return ResponseEntity.ok(interestRateService.getAllInterestRates());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterestRate> getInterestRateById(@PathVariable Long id) {
        return interestRateService.getInterestRateById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/product/{productCode}")
    public ResponseEntity<List<InterestRate>> getByProductCode(@PathVariable String productCode) {
        return ResponseEntity.ok(interestRateService.getInterestRatesByProduct(productCode));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterestRate> updateInterestRate(@PathVariable Long id, @RequestBody InterestRate rate) {
        rate.setId(id);
        return ResponseEntity.ok(interestRateService.saveInterestRate(rate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterestRate(@PathVariable Long id) {
        interestRateService.deleteInterestRate(id);
        return ResponseEntity.noContent().build();
    }
}
