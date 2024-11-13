package com.example.allAuth.DTOs;


import com.example.allAuth.enums.PackageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaxReturnDTO {

    private Long taxReturnId;
    private String assessmentYear;
    private Long taxpayerId;  // Reference to taxpayer by ID
    private LocalDate submissionDate;
    private String packageType;

    // You can expose documents and paymentStatus as DTOs or simply by their IDs
    private List<MultipartFile> documents;
    private PaymentStatusDTO paymentStatus;
}
