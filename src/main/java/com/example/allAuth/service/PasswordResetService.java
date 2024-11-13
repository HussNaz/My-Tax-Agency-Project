package com.example.allAuth.service;

import com.example.allAuth.Repositories.UserRepository;
import com.example.allAuth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Request a password reset
    public void resetPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
        // Generate a token and set it on the user
        user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setTokenExpirationDate(LocalDateTime.now().plusMinutes(10));
        userRepository.save(user);

        // Send email with the reset link (you should implement the correct URL for your application)
        String resetPasswordLink = "http://localhost:4200/reset-password?token=" + token;
        emailService.sendPasswordResetEmail(user.getEmail(), resetPasswordLink);
    }

    // Reset the password
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with token: " + token));
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setTokenExpirationDate(null);
        userRepository.save(user);
    }
}
