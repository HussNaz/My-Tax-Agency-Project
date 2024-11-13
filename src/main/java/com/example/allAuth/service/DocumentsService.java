package com.example.allAuth.service;

import com.example.allAuth.Repositories.DocumentsRepository;
import com.example.allAuth.Repositories.TaxReturnRepository;
import com.example.allAuth.Repositories.TaxpayerRepository;
import com.example.allAuth.Repositories.UserRepository;
import com.example.allAuth.entity.Documents;
import com.example.allAuth.entity.TaxReturn;
import com.example.allAuth.entity.Taxpayer;
import com.example.allAuth.entity.User;
import com.example.allAuth.enums.FileCategory;
import com.example.allAuth.interfaces.FileUploadService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class DocumentsService implements FileUploadService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DocumentsRepository documentsRepository;


    @Autowired
    private TaxReturnRepository taxReturnRepository;

    @Override
    @Transactional
    public void saveFile(MultipartFile file,String fileCategory, Long taxReturnId) throws IOException {

        TaxReturn taxReturn = taxReturnRepository.findByTaxReturnId(taxReturnId).orElseThrow(() -> new RuntimeException("Tax Return not found"));

        Documents documents = new Documents();
        documents.setFileName(file.getOriginalFilename());
        documents.setFileCategory(FileCategory.valueOf(fileCategory));
        documents.setFileType(file.getContentType());
        documents.setFileData(file.getBytes());
        documents.setTaxReturn(taxReturn);
        documents.setFileSize(file.getSize());

        documentsRepository.save(documents);
    }

    @Override
    public Documents getFileById(Long fileId) {
        return documentsRepository.findById(fileId).orElseThrow(() -> new RuntimeException("File not found"));
    }

    // Update an existing document
    @Transactional
    public Documents updateDocument(Long fileId, Documents newDocumentData) {
        return documentsRepository.findById(fileId).map(document -> {
            document.setFileName(newDocumentData.getFileName());
            document.setFileCategory(newDocumentData.getFileCategory());
            document.setFileType(newDocumentData.getFileType());
            document.setFileData(newDocumentData.getFileData());
            document.setFileSize(newDocumentData.getFileSize());
            document.setDescription(newDocumentData.getDescription());
            return documentsRepository.save(document);
        }).orElseThrow(() -> new RuntimeException("Document not found with ID: " + fileId));
    }

    // Delete a document by its ID
    public void deleteDocument(Long fileId) {
        if (documentsRepository.existsById(fileId)) {
            documentsRepository.deleteById(fileId);
        } else {
            throw new RuntimeException("Document not found with ID: " + fileId);
        }
    }


}
