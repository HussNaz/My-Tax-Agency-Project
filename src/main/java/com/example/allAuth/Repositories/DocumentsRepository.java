package com.example.allAuth.Repositories;

import com.example.allAuth.entity.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentsRepository extends JpaRepository<Documents, Long>{
    Optional<Documents> findByFileName(String filename);

    void deleteByTaxReturnTaxReturnId(Long taxReturnId);

    List<Documents> findByTaxReturnTaxReturnId(Long taxReturnId);
}
