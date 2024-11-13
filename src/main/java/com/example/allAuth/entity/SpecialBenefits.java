package com.example.allAuth.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "special_benefits")
public class SpecialBenefits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "benefit_id")
    private Long benefitId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taxpayer_id", nullable = false)
    @JsonIgnore
    private Taxpayer taxpayer;

    @Column(name = "is_war_wounded_freedom_fighter", columnDefinition = "BOOLEAN DEFAULT false")
    @Builder.Default
    private Boolean isWarWoundedFreedomFighter = false;

    @Column(name = "is_female", columnDefinition = "BOOLEAN DEFAULT false")
    @Builder.Default
    private Boolean isFemale = false;

    @Column(name = "is_third_gender", columnDefinition = "BOOLEAN DEFAULT false")
    @Builder.Default
    private Boolean isThirdGender = false;

    @Column(name = "is_disabled", columnDefinition = "BOOLEAN DEFAULT false")
    @Builder.Default
    private Boolean isDisabled = false;

    @Column(name = "is_aged_65_or_more", columnDefinition = "BOOLEAN DEFAULT false")
    @Builder.Default
    private Boolean isAged65OrMore = false;

    @Column(name = "is_parent_of_disabled_child", columnDefinition = "BOOLEAN DEFAULT false")
    @Builder.Default
    private Boolean isParentOfDisabledChild = false;
}
