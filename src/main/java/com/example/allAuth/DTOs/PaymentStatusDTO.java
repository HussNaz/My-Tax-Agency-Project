package com.example.allAuth.DTOs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentStatusDTO {
    private Long paymentId;
    private Long taxReturnId; // Assuming you want to expose the taxpayer's ID
    private Boolean isPaymentDone;
    private int paymentAmount;
    private String transactionId;
    private String paymentMode;
}
