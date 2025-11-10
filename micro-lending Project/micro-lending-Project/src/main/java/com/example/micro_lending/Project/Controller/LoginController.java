package com.example.micro_lending.Project.Controller;

import com.example.micro_lending.Project.Service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/request-login")
    public ResponseEntity<String> requestLogin(@RequestParam String email)throws jakarta.mail.MessagingException {
        loginService.sendLoginCode(email);
        return ResponseEntity.ok("Login code sent to email: " + email);
    }

    @PostMapping("/verify-login")
    public ResponseEntity<String> verifyLogin(
            @RequestParam String email,
            @RequestParam String code) {
        String jwtToken = loginService.verifyLoginCode(email, code);
        return ResponseEntity.ok(jwtToken);
    }
}
