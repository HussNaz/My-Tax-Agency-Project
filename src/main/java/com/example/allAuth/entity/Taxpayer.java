package com.example.allAuth.entity;

import com.example.allAuth.enums.ResidentStatus;
import com.example.allAuth.enums.SubmissionStatus;
import com.example.allAuth.enums.TaxpayerStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "taxpayer", indexes = {@Index(name = "taxpayer_national_id_index", columnList = "national_id", unique = true)
,@Index(name = "taxpayer_tin_index", columnList = "tin", unique = true)})
public class Taxpayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "taxpayer_id")
    private Long taxpayerId;

    @Column(name = "taxpayer_name", nullable = false, length = 100)
    private String taxpayerName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",unique = true)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "taxpayer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaxReturn> taxReturns;

    @Column(name = "national_id", length = 20, nullable = false, unique = true)
    private String nationalId;

    @Column(name = "tin", nullable = false, unique = true, length = 15)
    @NotEmpty(message = "Must need a TIN number")
    private String tin;

    @Column(name = "circle", length = 20)
    private String circle;

    @Column(name = "tax_zone", length = 50)
    private String taxZone;


    @Enumerated(EnumType.STRING)
    @Column(name = "resident_status", nullable = false)
    private ResidentStatus residentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "taxpayer_status", nullable = false)
    private TaxpayerStatus taxpayerStatus;

    @Column(name = "date_of_birth")
    private java.time.LocalDate dateOfBirth;


    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "is_gvt_employee", columnDefinition = "BOOLEAN DEFAULT false")
    @Builder.Default
    private Boolean isGvtEmployee = false;


    @OneToOne(mappedBy = "taxpayer", cascade = CascadeType.ALL, orphanRemoval = true)
    private SpecialBenefits specialBenefits;


    @Enumerated(EnumType.STRING)
    @Column(name = "submission_status", nullable = false)
    private SubmissionStatus submissionStatus = SubmissionStatus.IN_PROGRESS;

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