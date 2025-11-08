package com.example.micro_lending.Project.Service;

import com.example.micro_lending.Project.Entity.LoanAgreement;
import com.example.micro_lending.Project.Entity.PaymentTransaction;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // 1️⃣ Generic method to send an email
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();  // ✅ MimeMessage only
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("your-email@gmail.com");  // set your Gmail here
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);  // true = allow HTML content

        mailSender.send(message);
    }

    public String buildLoanAgreementEmailBody(LoanAgreement agreement) {
        return "Hello " + agreement.getLoanRequest().getApplicant().getFullName() + ",\n\n" +
                "Your loan has been approved!\n" +
                "Amount: " + agreement.getApprovedPrincipal() + "\n" +
                "Interest Rate: " + agreement.getAnnualInterestRate() + "%\n" +
                "Tenor: " + agreement.getTenorMonths() + " months\n" +
                "Disbursement Date: " + agreement.getDisbursementDate() + "\n" +
                "Maturity Date: " + agreement.getMaturityDate() + "\n\n" +
                "Thank you!";
    }


    // 3️⃣ Build Payment Transaction Email Content
    public String buildPaymentEmailBody(PaymentTransaction tx) {
        return "Hello " + tx.getPayer().getFullName() + ",\n\n" +
                "We received your payment successfully!\n" +
                "Amount Paid: " + tx.getAmount() + "\n" +
                "Installment No: " + tx.getRepaymentSchedule().getInstallmentNumber() + "\n" +
                "Payment Method: " + tx.getPaymentMethod() + "\n" +
                "Reference: " + tx.getGatewayReference() + "\n" +
                "Paid At: " + tx.getTransactionTime() + "\n\n" +
                "Thank you!";
    }
}
