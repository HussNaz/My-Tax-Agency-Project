package com.example.allAuth.service;

import com.example.allAuth.DTOs.TaxReturnDTO;
import com.example.allAuth.Repositories.DocumentsRepository;
import com.example.allAuth.Repositories.PaymentStatusRepository;
import com.example.allAuth.Repositories.TaxReturnRepository;
import com.example.allAuth.Repositories.TaxpayerRepository;
import com.example.allAuth.entity.Documents;
import com.example.allAuth.entity.PaymentStatus;
import com.example.allAuth.entity.TaxReturn;
import com.example.allAuth.entity.Taxpayer;
import com.example.allAuth.enums.PackageType;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaxReturnService {
    @Autowired
    private TaxpayerRepository taxpayerRepository;

    @Autowired
    private TaxReturnRepository taxReturnRepository;

    @Autowired
    private DocumentsRepository documentsRepository;

    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    // Fetch TaxReturn by ID
    public Optional<TaxReturn> findById(Long id) {
        return taxReturnRepository.findById(id);
    }

    @Transactional
    public TaxReturn submitTaxReturn(TaxReturnDTO taxReturnDTO) throws IOException {
        Taxpayer taxpayer = taxpayerRepository.findById(taxReturnDTO.getTaxpayerId()).orElseThrow(() -> new EntityNotFoundException("Taxpayer not found"));

        // Create new TaxReturn for the assessment year
        TaxReturn taxReturn = new TaxReturn();
        taxReturn.setAssessmentYear(taxReturnDTO.getAssessmentYear());
        taxReturn.setTaxpayer(taxpayer);
        taxReturn.setPackageType(PackageType.valueOf(taxReturnDTO.getPackageType()));
        taxReturn.setSubmissionDate(LocalDate.now());

        // Save tax return
        taxReturn = taxReturnRepository.save(taxReturn);

        // Handle file uploads
        List<Documents> documentList = new ArrayList<>();
        for (MultipartFile file : taxReturnDTO.getDocuments()) {
            Documents document = new Documents();
            document.setTaxReturn(taxReturn);
            document.setFileName(file.getOriginalFilename());
            document.setFileType(file.getContentType());
            document.setFileSize(file.getSize());
            document.setFileData(file.getBytes());
            documentList.add(document);
        }
        documentsRepository.saveAll(documentList);

        // Handle payment statuses
        PaymentStatus paymentStatus = new PaymentStatus();
        paymentStatus.setIsPaymentDone(taxReturnDTO.getPaymentStatus().getIsPaymentDone());
        paymentStatus.setPaymentAmount(taxReturnDTO.getPaymentStatus().getPaymentAmount());
        paymentStatus.setTransactionId(taxReturnDTO.getPaymentStatus().getTransactionId());
        paymentStatus.setPaymentMode(taxReturnDTO.getPaymentStatus().getPaymentMode());
        paymentStatus.setTaxReturn(taxReturn);


        paymentStatusRepository.save(paymentStatus);


        return taxReturn;
    }

    public List<TaxReturn> findAll() {
        return taxReturnRepository.findAll();
    }

    public List<TaxReturn> findByTaxpayerId(Long taxpayerId) {
        return taxReturnRepository.findByTaxpayerTaxpayerId(taxpayerId);
    }

    public TaxReturn findByAssessmentYear(String assessmentYear) {
        return taxReturnRepository.findByAssessmentYear(assessmentYear);
    }


    public TaxReturn findByTaxpayerIdAndAssessmentYear(Long taxpayerId, String assessmentYear) {
        return taxReturnRepository.findByTaxpayerTaxpayerIdAndAssessmentYear(taxpayerId, assessmentYear);
    }


    @Transactional
    public TaxReturn updateTaxReturn(Long id, TaxReturnDTO taxReturnDTO) throws IOException {
        TaxReturn taxReturn = taxReturnRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tax Return not found"));
        taxReturn.setAssessmentYear(taxReturnDTO.getAssessmentYear());
        taxReturn.setPackageType(PackageType.valueOf(taxReturnDTO.getPackageType()));

        // Update documents
        List<Documents> documentList = new ArrayList<>();
        for (MultipartFile file : taxReturnDTO.getDocuments()) {
            Documents document = new Documents();
            document.setTaxReturn(taxReturn);
            document.setFileName(file.getOriginalFilename());
            document.setFileType(file.getContentType());
            document.setFileSize(file.getSize());
            document.setFileData(file.getBytes());
            documentList.add(document);
        }
        documentsRepository.deleteByTaxReturnTaxReturnId(id);
        documentsRepository.saveAll(documentList);


        return taxReturnRepository.save(taxReturn);
    }

    @Transactional
    public void deleteTaxReturn(Long id) {
        taxReturnRepository.deleteById(id);
    }


}
