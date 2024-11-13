package com.example.allAuth.entity;

import com.example.allAuth.enums.PackageType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Setter
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaxReturn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tax_return_id")
    private Long taxReturnId;

    @Column(name = "assessment_year", nullable = false)
    private String assessmentYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "package_type", nullable = false)
    private PackageType packageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taxpayer_id")
    @JsonIgnore
    private Taxpayer taxpayer;

    @OneToMany(mappedBy = "taxReturn", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    List<Documents> documents;

    @OneToOne(mappedBy = "taxReturn", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private PaymentStatus paymentStatus;

    @Column(name = "submission_date")
    private LocalDate submissionDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "last_modified_at")
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
