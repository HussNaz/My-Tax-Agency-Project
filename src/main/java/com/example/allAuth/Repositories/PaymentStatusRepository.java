package com.example.allAuth.Repositories;

import com.example.allAuth.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {

    Optional<PaymentStatus> findByTaxReturnTaxReturnId(Long id);
}
