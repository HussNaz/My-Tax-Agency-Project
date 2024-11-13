package com.example.allAuth.DTOs;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class TaxpayerDTO {
    private Long taxpayerId;
    private String taxpayerName;// Reference to the user
    private String nationalId;
    private String tin;
    private String circle;
    private String taxZone;
    private String residentStatus;
    private String taxpayerStatus;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String address;
    private Boolean isGvtEmployee;// List of DocumentDTO
    private SpecialBenefitsDTO specialBenefits;  // Reference to SpecialBenefitsDTO
    private String submissionStatus;
    private LocalDateTime createdAt;
    private List<TaxReturnDTO> taxReturns;
}
