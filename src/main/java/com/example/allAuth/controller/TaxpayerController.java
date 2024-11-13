package com.example.allAuth.controller;

import com.example.allAuth.DTOs.TaxpayerDTO;
import com.example.allAuth.entity.Taxpayer;
import com.example.allAuth.entity.User;
import com.example.allAuth.service.TaxpayerService;
import com.example.allAuth.userService.UserService;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/taxpayer")
public class TaxpayerController {

    @Autowired
    TaxpayerService taxpayerService;

    @Autowired
    UserService userService;

    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN','ROLE_USER')")
    public Taxpayer registerTaxpayer(@RequestBody TaxpayerDTO taxpayerRegistrationDTO) throws IOException, java.io.IOException {
        // Get the logged-in user from Spring Security
        Authentication  authentication= SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User loggedInUser = userService.findByUsername(username); // Assume you have a method to find user by username

//        // Register the taxpayer
        return taxpayerService.registerTaxpayer(taxpayerRegistrationDTO, loggedInUser);
    }

    @PutMapping("/update/{taxpayerId}")
    public ResponseEntity<Taxpayer> updateTaxpayer(@PathVariable Long taxpayerId, @ModelAttribute TaxpayerDTO taxpayerUpdateDTO) {
        // Update the taxpayer
        Taxpayer taxpayer = taxpayerService.updateTaxpayer(taxpayerId, taxpayerUpdateDTO);

        return ResponseEntity.ok(taxpayer);
    }

    @DeleteMapping("/delete/{taxpayerId}")
    public ResponseEntity<?> deleteTaxpayer(@PathVariable Long taxpayerId) {
        // Delete the taxpayer
        taxpayerService.deleteTaxpayer(taxpayerId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getTaxpayerById/{taxpayerId}")
    public Taxpayer getTaxpayer(@PathVariable Long taxpayerId) {
        // Get the taxpayer
        return taxpayerService.findById(taxpayerId).get();
    }

    @GetMapping("/getTaxpayers")
    public Iterable<Taxpayer> getTaxpayers() {
        // Get all taxpayers
        return taxpayerService.findAll();
    }

    @GetMapping("/getTaxpayerByTin/{tin}")
    public Taxpayer getTaxpayerByTin(@PathVariable String tin) {
        // Get the taxpayer
        return taxpayerService.findByTin(tin).get();
    }
}

