package com.example.allAuth.Repositories;

import com.example.allAuth.entity.Taxpayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaxpayerRepository extends JpaRepository<Taxpayer, Long> {

    Optional<Taxpayer> findByTin(String tin);
}
