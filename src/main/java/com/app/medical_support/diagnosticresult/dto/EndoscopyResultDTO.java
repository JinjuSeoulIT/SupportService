package com.app.medical_support.diagnosticresult.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EndoscopyResultDTO {

    private String endoscopyResultId;
    private String endoscopyExamId;
    private String testExecutionId;
    private String detailCode;
    private Long patientId;
    private String patientName;
    private String departmentName;
    private String performerId;
    private String performerName;
    private String finding;
    private String biopsyYn;
    private LocalDateTime confirmedAt;
    private String readerId;
    private String status;
    private LocalDateTime createdAt;

    public EndoscopyResultDTO(
            String endoscopyResultId,
            String endoscopyExamId,
            String testExecutionId,
            String detailCode,
            Long patientId,
            String patientName,
            String departmentName,
            String performerId,
            String performerName,
            String finding,
            String biopsyYn,
            LocalDateTime confirmedAt,
            String readerId,
            String status,
            LocalDateTime createdAt
    ) {
        this.endoscopyResultId = endoscopyResultId;
        this.endoscopyExamId = endoscopyExamId;
        this.testExecutionId = testExecutionId;
        this.detailCode = detailCode;
        this.patientId = patientId;
        this.patientName = patientName;
        this.departmentName = departmentName;
        this.performerId = performerId;
        this.performerName = performerName;
        this.finding = finding;
        this.biopsyYn = biopsyYn;
        this.confirmedAt = confirmedAt;
        this.readerId = readerId;
        this.status = status;
        this.createdAt = createdAt;
    }
}
