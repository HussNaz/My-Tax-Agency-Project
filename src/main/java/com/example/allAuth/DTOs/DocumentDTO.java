package com.example.allAuth.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class DocumentDTO {

    private Long fileId;
    private Long taxReturnId;
    private String fileName;
    private String fileCategory;
    private String fileType;
    private Long fileSize;
    private String description;
    private LocalDateTime uploadTime;
    
}
