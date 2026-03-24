package com.app.medical_support.diagnosticexecution.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ImagingDTO {

    private String imagingExamId;
    private String visitId;
    private String imagingType;
    private String examStatusYn;
    private LocalDateTime examAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
