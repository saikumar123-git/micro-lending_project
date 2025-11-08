package com.example.micro_lending.Project.Service;

import com.example.micro_lending.Project.Entity.InterestRate;
import com.example.micro_lending.Project.Entity.LoanRequest;
import com.example.micro_lending.Project.Entity.LoanStatus;
import com.example.micro_lending.Project.Entity.User;
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
    @Autowired
    private InterestRateService interestRateService; // ✅ fetch rates dynamically

    private final LoanRequestRepository loanRequestRepository;

    public LoanRequestService(LoanRequestRepository loanRequestRepository) {
        this.loanRequestRepository = loanRequestRepository;
    }

    public LoanRequest createLoanRequest(LoanRequest loanRequest) {
        if (loanRequest.getApplicant() == null || loanRequest.getApplicant().getId() == null) {
            throw new IllegalArgumentException("Applicant ID is required");
        }

        loanRequest.setStatus(LoanStatus.PENDING);
        loanRequest.setAppliedAt(LocalDateTime.now());

        BigDecimal rate = interestRateService.getInterestRatesByProduct(loanRequest.getProductCode())
                .stream()
                .filter(r -> r.getValidFrom().isBefore(LocalDateTime.now().toLocalDate()) &&
                        (r.getValidTo() == null || r.getValidTo().isAfter(LocalDateTime.now().toLocalDate())))
                .map(InterestRate::getAnnualRate)
                .findFirst()
                .orElse(loanRequest.getRequestedInterestRate());

        loanRequest.setRequestedInterestRate(rate);

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

        // ✅ Auto-create LoanAgreement when APPROVED
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
