package com.example.allAuth.controller;

import com.example.allAuth.DTOs.PaymentStatusDTO;
import com.example.allAuth.service.PaymentStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment-status")
public class PaymentStatusController {

    @Autowired
    private PaymentStatusService paymentStatusService;

    // CREATE PaymentStatus
    @PostMapping("/create")
    public ResponseEntity<PaymentStatusDTO> createPaymentStatus(@RequestBody PaymentStatusDTO paymentStatusDTO) {
        PaymentStatusDTO createdPayment = paymentStatusService.createPaymentStatus(paymentStatusDTO);
        return ResponseEntity.ok(createdPayment);
    }

    // READ PaymentStatus by ID
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentStatusDTO> getPaymentStatusById(@PathVariable Long paymentId) {
        PaymentStatusDTO paymentStatusDTO = paymentStatusService.getPaymentStatusById(paymentId);
        return ResponseEntity.ok(paymentStatusDTO);
    }

    // UPDATE PaymentStatus
    @PutMapping("/update/{paymentId}")
    public ResponseEntity<PaymentStatusDTO> updatePaymentStatus(@PathVariable Long paymentId, @RequestBody PaymentStatusDTO paymentStatusDTO) {
        PaymentStatusDTO updatedPayment = paymentStatusService.updatePaymentStatus(paymentId, paymentStatusDTO);
        return ResponseEntity.ok(updatedPayment);
    }

    // DELETE PaymentStatus
    @DeleteMapping("/delete/{paymentId}")
    public ResponseEntity<Void> deletePaymentStatus(@PathVariable Long paymentId) {
        paymentStatusService.deletePaymentStatus(paymentId);
        return ResponseEntity.noContent().build();
    }
}
