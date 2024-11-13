package com.example.allAuth.controller;

import com.example.allAuth.entity.User;
import com.example.allAuth.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    // Other endpoints for user management

    @GetMapping("/admin")
    public String admin() {
        return "Admin page";
    }
    @GetMapping("/user")
    public String user() {
        return "User page";
    }
    @GetMapping("/public")
    public String publicPage() {
        return "Public page";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "Forgot password page";
    }

    @GetMapping("/reset-password")
    public String resetPassword() {
        return "Reset password page";
    }

    @GetMapping("/verify-email")
    public String verifyEmail() {
        return "Verify email page";
    }

}
