package com.example.allAuth.DTOs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SpecialBenefitsDTO {
    private Long benefitId;
    private Long taxpayerId; // Only include the taxpayer ID for reference, not the full entity
    private Boolean isWarWoundedFreedomFighter;
    private Boolean isFemale;
    private Boolean isThirdGender;
    private Boolean isDisabled;
    private Boolean isAged65OrMore;
    private Boolean isParentOfDisabledChild;
}
