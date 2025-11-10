package com.example.micro_lending.Project.Service;

import com.example.micro_lending.Project.Entity.LoanRequest;
import com.example.micro_lending.Project.Entity.LoanStatus;
import com.example.micro_lending.Project.Repository.LoanRequestRepository;
import com.example.micro_lending.Project.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LoanRequestService {
    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private LoanAgreementService loanAgreementService;
    @Autowired
    private UserRepository userRepository;
    private final LoanRequestRepository loanRequestRepository;


    private static final BigDecimal BASE_RATE = new BigDecimal("0.020");      // 2.0% minimum
    private static final BigDecimal TENOR_RISK_MONTHLY = new BigDecimal("0.0005"); // 0.05% per month
    private static final BigDecimal SMALL_LOAN_PREMIUM = new BigDecimal("0.050"); // 5.0% premium for loans < $5,000
    private static final BigDecimal SMALL_LOAN_THRESHOLD = new BigDecimal("5000");

    public LoanRequestService(LoanRequestRepository loanRequestRepository) {
        this.loanRequestRepository = loanRequestRepository;
    }

    private BigDecimal calculateDynamicInterestRate(BigDecimal principal, Integer tenor) {
        BigDecimal rate = BASE_RATE;
        BigDecimal tenorPremium = TENOR_RISK_MONTHLY.multiply(new BigDecimal(tenor));
        rate = rate.add(tenorPremium);
        if (principal.compareTo(SMALL_LOAN_THRESHOLD) < 0) {
            rate = rate.add(SMALL_LOAN_PREMIUM);
        }
        return rate.setScale(4, BigDecimal.ROUND_HALF_UP);
    }

    public LoanRequest createLoanRequest(LoanRequest loanRequest) {
        if (loanRequest.getApplicant() == null || loanRequest.getApplicant().getId() == null) {
            throw new IllegalArgumentException("Applicant ID is required");
        }
        loanRequest.setStatus(LoanStatus.PENDING);
        loanRequest.setAppliedAt(LocalDateTime.now());
        BigDecimal principal = loanRequest.getPrincipalAmount();
        Integer tenor = loanRequest.getTenorMonths();
        BigDecimal finalRate = calculateDynamicInterestRate(principal, tenor);
        loanRequest.setFinalInterestRate(finalRate);
        return loanRequestRepository.save(loanRequest);
    }

    public List<LoanRequest> getAllLoanRequests() {
        return loanRequestRepository.findAll();
    }

    public Optional<LoanRequest> getUserById(Long id) {
        return loanRequestRepository.findById(id);
    }

    public LoanRequest updateLoanRequestStatus(Long id, LoanStatus status, String adminUsername) throws jakarta.mail.MessagingException {
        LoanRequest loanRequest = loanRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan Request not found"));
        loanRequest.setStatus(status);
        LoanRequest saved = loanRequestRepository.save(loanRequest);

        if (status == LoanStatus.APPROVED) {
            loanAgreementService.createLoanAgreement(saved);
        }

        auditLogService.createLog(
                "LoanRequest",
                saved.getId(),
                "REVIEW",
                adminUsername,
                "Loan request " + status + " by admin"
        );
        return saved;
    }
}