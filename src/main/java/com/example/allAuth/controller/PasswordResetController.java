package com.example.allAuth.controller;

import com.example.allAuth.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/password-reset")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    // Request a password reset
    @RequestMapping("/request")
    public String requestPasswordReset(@RequestParam String email) {
        passwordResetService.resetPassword(email);
        return "Password reset link sent to " + email;
    }

    // Reset the password
    @RequestMapping("/reset")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        passwordResetService.resetPassword(token, newPassword);
        return "Password reset successful";
    }
}
