package com.app.medical_support.diagnosticresult.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ImagingResultDTO {

    private String imagingResultId;
    private String imagingExamId;
    private String readingSummary;
    private String readingDetail;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
