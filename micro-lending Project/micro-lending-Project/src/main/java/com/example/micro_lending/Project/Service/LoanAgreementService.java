package com.example.micro_lending.Project.Service;

import com.example.micro_lending.Project.Entity.*;
import com.example.micro_lending.Project.Repository.LoanAgrrementRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoanAgreementService {

    private final LoanAgrrementRepository loanAgreementRepository;
    private final EmailService emailService;

    public LoanAgreementService(LoanAgrrementRepository loanAgreementRepository,
                                EmailService emailService) {
        this.loanAgreementRepository = loanAgreementRepository;
        this.emailService = emailService;
    }

    public LoanAgreement createLoanAgreement(LoanRequest loanRequest) throws jakarta.mail.MessagingException {
        LoanAgreement agreement = LoanAgreement.builder()
                .loanRequest(loanRequest)
                .approvedPrincipal(loanRequest.getPrincipalAmount())
                .annualInterestRate(loanRequest.getRequestedInterestRate())
                .tenorMonths(loanRequest.getTenorMonths())
                .disbursementDate(LocalDate.now())
                .maturityDate(LocalDate.now().plusMonths(loanRequest.getTenorMonths()))
                .schedules(new ArrayList<>())
                .build();

        BigDecimal monthlyPrincipal = agreement.getApprovedPrincipal()
                .divide(BigDecimal.valueOf(agreement.getTenorMonths()), BigDecimal.ROUND_HALF_UP);
        BigDecimal monthlyInterest = agreement.getApprovedPrincipal()
                .multiply(agreement.getAnnualInterestRate())
                .divide(BigDecimal.valueOf(100 * agreement.getTenorMonths()), BigDecimal.ROUND_HALF_UP);

        for (int i = 1; i <= agreement.getTenorMonths(); i++) {
            RepaymentSchedule schedule = RepaymentSchedule.builder()
                    .loanAgreement(agreement)
                    .installmentNumber(i)
                    .dueDate(LocalDate.now().plusMonths(i))
                    .principalComponent(monthlyPrincipal)
                    .interestComponent(monthlyInterest)
                    .totalDue(monthlyPrincipal.add(monthlyInterest))
                    .paymentStatus(PaymentStatus.PENDING)
                    .build();
            agreement.getSchedules().add(schedule);
        }

        LoanAgreement savedAgreement = loanAgreementRepository.save(agreement);

        emailService.sendEmail(
                loanRequest.getApplicant().getEmail(),
                "Loan Approved",
                emailService.buildLoanAgreementEmailBody(savedAgreement)
        );

        return savedAgreement;
    }

    public List<LoanAgreement> getAllLoanAgreements() {
        return loanAgreementRepository.findAll();
    }

    public Optional<LoanAgreement> getLoanAgreementById(Long id) {
        return loanAgreementRepository.findById(id);
    }
}
