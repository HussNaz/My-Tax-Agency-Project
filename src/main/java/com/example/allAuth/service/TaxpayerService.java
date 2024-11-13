package com.example.allAuth.service;

import com.example.allAuth.DTOs.TaxpayerDTO;
import com.example.allAuth.Repositories.DocumentsRepository;
import com.example.allAuth.Repositories.TaxpayerRepository;
import com.example.allAuth.entity.Documents;
import com.example.allAuth.entity.SpecialBenefits;
import com.example.allAuth.entity.Taxpayer;
import com.example.allAuth.entity.User;
import com.example.allAuth.enums.ResidentStatus;
import com.example.allAuth.enums.TaxpayerStatus;
import io.jsonwebtoken.io.IOException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaxpayerService {
    @Autowired
    private TaxpayerRepository taxpayerRepository;

    @Autowired
    private DocumentsRepository documentsRepository;

    // Fetch Taxpayer by ID
    public Optional<Taxpayer> findById(Long id) {
        return taxpayerRepository.findById(id);
    }

    public Taxpayer registerTaxpayer(TaxpayerDTO dto, User loggedInUser) throws IOException, java.io.IOException {
        // Create new Taxpayer entity
        Taxpayer taxpayer = new Taxpayer();
        taxpayer.setTaxpayerName(dto.getTaxpayerName());
        taxpayer.setNationalId(dto.getNationalId());
        taxpayer.setTin(dto.getTin());
        taxpayer.setCircle(dto.getCircle());
        taxpayer.setTaxZone(dto.getTaxZone());
        if(dto.getResidentStatus() != null) {
            try {
                taxpayer.setResidentStatus(ResidentStatus.valueOf(dto.getResidentStatus()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid ResidentStatus value: " + dto.getResidentStatus());
            }
        }

        if (dto.getTaxpayerStatus() != null) {
            try {
                taxpayer.setTaxpayerStatus(TaxpayerStatus.valueOf(dto.getTaxpayerStatus()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid TaxpayerStatus value: " + dto.getTaxpayerStatus());
            }
        }
        taxpayer.setDateOfBirth(dto.getDateOfBirth());
        taxpayer.setAddress(dto.getAddress());
        taxpayer.setIsGvtEmployee(dto.getIsGvtEmployee());
        taxpayer.setUser(loggedInUser);

        SpecialBenefits mapDtotoEntity = getSpecialBenefits(dto, taxpayer);

        taxpayer.setSpecialBenefits(mapDtotoEntity);

        try {
            taxpayerRepository.save(taxpayer);
            return taxpayer;
        } catch (Exception e) {
            // handle the exception, e.g., log the error and return a meaningful error message
            // for example:
            throw new RuntimeException("Failed to save taxpayer", e);
        }
    }

    private static @NotNull SpecialBenefits getSpecialBenefits(TaxpayerDTO dto, Taxpayer taxpayer) {
        SpecialBenefits mapDtotoEntity = new SpecialBenefits();
        mapDtotoEntity.setIsWarWoundedFreedomFighter(dto.getSpecialBenefits().getIsWarWoundedFreedomFighter());
        mapDtotoEntity.setIsDisabled(dto.getSpecialBenefits().getIsDisabled());
        mapDtotoEntity.setIsThirdGender(dto.getSpecialBenefits().getIsThirdGender());
        mapDtotoEntity.setIsAged65OrMore(dto.getSpecialBenefits().getIsAged65OrMore());
        mapDtotoEntity.setIsParentOfDisabledChild(dto.getSpecialBenefits().getIsParentOfDisabledChild());
        mapDtotoEntity.setIsFemale(dto.getSpecialBenefits().getIsFemale());
        mapDtotoEntity.setTaxpayer(taxpayer);
        return mapDtotoEntity;
    }

    // Update an existing Taxpayer
    public Taxpayer updateTaxpayer(Long id, TaxpayerDTO updatedTaxpayer) {
        return taxpayerRepository.findById(id).map(taxpayer -> {
            taxpayer.setTaxpayerName(updatedTaxpayer.getTaxpayerName());
            taxpayer.setNationalId(updatedTaxpayer.getNationalId());
            taxpayer.setTin(updatedTaxpayer.getTin());
            taxpayer.setCircle(updatedTaxpayer.getCircle());
            taxpayer.setTaxZone(updatedTaxpayer.getTaxZone());
            taxpayer.setResidentStatus(ResidentStatus.valueOf(updatedTaxpayer.getResidentStatus()));
            taxpayer.setTaxpayerStatus(TaxpayerStatus.valueOf(updatedTaxpayer.getTaxpayerStatus()));
            taxpayer.setDateOfBirth(updatedTaxpayer.getDateOfBirth());
            taxpayer.setAddress(updatedTaxpayer.getAddress());
            taxpayer.setIsGvtEmployee(updatedTaxpayer.getIsGvtEmployee());
            // Update other fields as needed
            return taxpayerRepository.save(taxpayer);
        }).orElseThrow(() -> new ResourceNotFoundException("Taxpayer not found with id " + id));
    }

    // Delete a Taxpayer by ID
    public void deleteTaxpayer(Long id) {
        Taxpayer taxpayer = taxpayerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Taxpayer not found with id " + id));
        taxpayerRepository.delete(taxpayer);
    }

    public List<Taxpayer> findAll() {
        return taxpayerRepository.findAll();
    }

    public Optional<Taxpayer> findByTin(String tin) {
        return taxpayerRepository.findByTin(tin);
    }
}
