package com.example.allAuth.service;

import com.example.allAuth.DTOs.PaymentStatusDTO;
import com.example.allAuth.Repositories.PaymentStatusRepository;
import com.example.allAuth.Repositories.TaxReturnRepository;
import com.example.allAuth.entity.PaymentStatus;
import com.example.allAuth.entity.TaxReturn;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentStatusService {
    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    @Autowired
    private TaxReturnRepository taxReturnRepository;


    // CREATE PaymentStatus
    @Transactional
    public PaymentStatusDTO createPaymentStatus(PaymentStatusDTO paymentStatusDTO) {
        PaymentStatus paymentStatus = new PaymentStatus();

        // Set values from DTO
        paymentStatus.setIsPaymentDone(paymentStatusDTO.getIsPaymentDone());
        paymentStatus.setPaymentAmount(paymentStatusDTO.getPaymentAmount());
        paymentStatus.setTransactionId(paymentStatusDTO.getTransactionId());
        paymentStatus.setPaymentMode(paymentStatusDTO.getPaymentMode());

        // Fetch associated TaxReturn and set the relation
        Optional<TaxReturn> taxReturnOptional = taxReturnRepository.findById(paymentStatusDTO.getTaxReturnId());
        if (taxReturnOptional.isEmpty()) {
            throw new RuntimeException("Tax Return not found");
        }
        paymentStatus.setTaxReturn(taxReturnOptional.get());

        PaymentStatus savedPaymentStatus = paymentStatusRepository.save(paymentStatus);
        return mapToDTO(savedPaymentStatus);
    }

    // READ PaymentStatus by ID
    public PaymentStatusDTO getPaymentStatusById(Long paymentId) {
        Optional<PaymentStatus> paymentStatusOptional = paymentStatusRepository.findById(paymentId);
        if (paymentStatusOptional.isEmpty()) {
            throw new RuntimeException("Payment status not found");
        }
        return mapToDTO(paymentStatusOptional.get());
    }

    // UPDATE PaymentStatus
    @Transactional
    public PaymentStatusDTO updatePaymentStatus(Long paymentId, PaymentStatusDTO paymentStatusDTO) {
        Optional<PaymentStatus> paymentStatusOptional = paymentStatusRepository.findById(paymentId);
        if (paymentStatusOptional.isEmpty()) {
            throw new RuntimeException("Payment status not found");
        }

        PaymentStatus paymentStatus = paymentStatusOptional.get();

        // Update the payment status fields
        paymentStatus.setIsPaymentDone(paymentStatusDTO.getIsPaymentDone());
        paymentStatus.setPaymentAmount(paymentStatusDTO.getPaymentAmount());
        paymentStatus.setTransactionId(paymentStatusDTO.getTransactionId());
        paymentStatus.setPaymentMode(paymentStatusDTO.getPaymentMode());

        // Update the tax return relationship if needed
        if (!paymentStatus.getTaxReturn().getTaxReturnId().equals(paymentStatusDTO.getTaxReturnId())) {
            Optional<TaxReturn> taxReturnOptional = taxReturnRepository.findById(paymentStatusDTO.getTaxReturnId());
            if (taxReturnOptional.isEmpty()) {
                throw new RuntimeException("Tax Return not found");
            }
            paymentStatus.setTaxReturn(taxReturnOptional.get());
        }

        PaymentStatus updatedPaymentStatus = paymentStatusRepository.save(paymentStatus);
        return mapToDTO(updatedPaymentStatus);
    }

    // DELETE PaymentStatus
    public void deletePaymentStatus(Long paymentId) {
        if (!paymentStatusRepository.existsById(paymentId)) {
            throw new RuntimeException("Payment status not found");
        }
        paymentStatusRepository.deleteById(paymentId);
    }

    // Helper method to map entity to DTO
    private PaymentStatusDTO mapToDTO(PaymentStatus paymentStatus) {
        PaymentStatusDTO dto = new PaymentStatusDTO();
        dto.setPaymentId(paymentStatus.getPaymentId());
        dto.setIsPaymentDone(paymentStatus.getIsPaymentDone());
        dto.setPaymentAmount(paymentStatus.getPaymentAmount());
        dto.setTransactionId(paymentStatus.getTransactionId());
        dto.setPaymentMode(paymentStatus.getPaymentMode());
        dto.setTaxReturnId(paymentStatus.getTaxReturn().getTaxReturnId());
        return dto;
    }
}
