package com.example.micro_lending.Project.Service;

import com.example.micro_lending.Project.Configuration.JWTService;
import com.example.micro_lending.Project.Configuration.UserPrincipal;
import com.example.micro_lending.Project.Entity.User;
import com.example.micro_lending.Project.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class LoginService {

    private final UserRepository userRepository;
    private final EmailService mailService;
    private final JWTService jwtService;

    private final Map<String, LoginCode> loginCodes = new HashMap<>();

    public LoginService(UserRepository userRepository, EmailService mailService, JWTService jwtService) {
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.jwtService = jwtService;
    }

    public void sendLoginCode(String email)throws jakarta.mail.MessagingException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String code = String.format("%06d", new Random().nextInt(999999));
        LoginCode loginCode = new LoginCode(code, LocalDateTime.now().plusMinutes(5));
        loginCodes.put(email, loginCode);

        String body = "Hello " + user.getFullName() + ",\n\n" +
                "Your login code is: " + code + "\n" +
                "This code will expire in 5 minutes.\n\n" +
                "Thank you!";
        mailService.sendEmail(email, "Your Login Code", body);
    }

    public String verifyLoginCode(String email, String code) {
        LoginCode loginCode = loginCodes.get(email);

        if (loginCode == null || loginCode.getExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Code expired or not found");
        }

        if (!loginCode.getCode().equals(code)) {
            throw new RuntimeException("Invalid code");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        loginCodes.remove(email);
        return jwtService.generateToken(new UserPrincipal(user));
    }
    private static class LoginCode {
        private final String code;
        private final LocalDateTime expiry;

        public LoginCode(String code, LocalDateTime expiry) {
            this.code = code;
            this.expiry = expiry;
        }

        public String getCode() {
            return code;
        }

        public LocalDateTime getExpiry() {
            return expiry;
        }
    }
}
