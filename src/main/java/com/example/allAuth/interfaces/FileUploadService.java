package com.example.allAuth.interfaces;

import com.example.allAuth.entity.Documents;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {
    void saveFile(MultipartFile file, String fileCategory, Long userId) throws IOException;
    Documents getFileById(Long fileId);

}
