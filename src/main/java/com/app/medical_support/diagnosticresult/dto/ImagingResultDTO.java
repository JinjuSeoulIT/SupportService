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
    private String testExecutionId;
    private String detailCode;
    private Long patientId;
    private String patientName;
    private String departmentName;
    private String performerId;
    private String performerName;
    private String readingSummary;
    private String readingDetail;
    private String status;
    private LocalDateTime createdAt;

    public ImagingResultDTO(
            String imagingResultId,
            String imagingExamId,
            String testExecutionId,
            String detailCode,
            Long patientId,
            String patientName,
            String departmentName,
            String performerId,
            String performerName,
            String readingSummary,
            String readingDetail,
            String status,
            LocalDateTime createdAt
    ) {
        this.imagingResultId = imagingResultId;
        this.imagingExamId = imagingExamId;
        this.testExecutionId = testExecutionId;
        this.detailCode = detailCode;
        this.patientId = patientId;
        this.patientName = patientName;
        this.departmentName = departmentName;
        this.performerId = performerId;
        this.performerName = performerName;
        this.readingSummary = readingSummary;
        this.readingDetail = readingDetail;
        this.status = status;
        this.createdAt = createdAt;
    }
}
