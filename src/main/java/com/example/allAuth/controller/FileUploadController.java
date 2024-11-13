package com.example.allAuth.controller;

import com.example.allAuth.entity.Documents;
import com.example.allAuth.enums.FileCategory;
import com.example.allAuth.service.DocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/fileUpload")
public class FileUploadController {


    @Autowired
    private DocumentsService documentsService;

    @PostMapping("/upload/{userId}")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("fileCategory") String fileCategory ,@PathVariable Long userId) {
        if (file.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Select a file to upload");
        }

        try {
            documentsService.saveFile(file,fileCategory,userId);
            return ResponseEntity.status(HttpStatus.CREATED).body("File uploaded successfully"+file.getOriginalFilename());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
        }
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> getFileByID(@PathVariable Long fileId) {
        Documents documents = documentsService.getFileById(fileId);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + documents.getFileName() + "\"")
                .body(documents.getFileData());
    }

    // Update a document by ID
    @PutMapping("/updateDocument/{id}")
    public ResponseEntity<Documents> updateDocument(
            @PathVariable("id") Long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("description") String description,
            @RequestParam("fileCategory") FileCategory fileCategory) {

        try {
            Documents document = new Documents();
            document.setFileName(file.getOriginalFilename());
            document.setFileType(file.getContentType());
            document.setFileSize(file.getSize());
            document.setFileData(file.getBytes());
            document.setDescription(description);
            document.setFileCategory(fileCategory);

            Documents updatedDocument = documentsService.updateDocument(id, document);
            return ResponseEntity.ok(updatedDocument);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    // Delete a document by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable("id") Long id) {
        documentsService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
}
