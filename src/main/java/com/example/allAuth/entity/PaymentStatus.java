package com.example.allAuth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "payment_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tax_return_id")
    @JsonIgnore
    private TaxReturn taxReturn;


    @Column(name = "is_payment_done", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isPaymentDone=false;

    @Column(name = "payment_amount")
    private int paymentAmount;

    @Column(name = "transaction_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime transactionDate= LocalDateTime.now();

    @Column(name = "transaction_id")
    private String transactionId;


    @Column(name = "payment_mode")
    private String paymentMode;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastModifiedAt = LocalDateTime.now();
    }
}
