package com.example.allAuth.Repositories;

import com.example.allAuth.entity.TaxReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaxReturnRepository extends JpaRepository<TaxReturn, Long> {
    List<TaxReturn> findByTaxpayerTaxpayerId(Long taxpayerId);

    TaxReturn findByAssessmentYear(String assessmentYear);

    Optional<TaxReturn> findByTaxReturnId(Long taxReturnId);

    TaxReturn findByTaxpayerTaxpayerIdAndAssessmentYear(Long taxpayerId, String assessmentYear);
}
