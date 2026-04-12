package com.app.medical_support.diagnosticresult.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PathologyResultDTO {

    private String pathologyExamResultId;
    private String pathologyExamId;
    private String testExecutionId;
    private String detailCode;
    private Long patientId;
    private String patientName;
    private String departmentName;
    private String performerId;
    private String performerName;
    private String resultManagerId;
    private String resultManagerName;
    private String resultSummary;
    private LocalDateTime judgedAt;
    private LocalDateTime confirmedAt;
    private String readerId;
    private String diagnosisName;
    private String status;
    private LocalDateTime createdAt;

    public PathologyResultDTO(
            String pathologyExamResultId,
            String pathologyExamId,
            String testExecutionId,
            String detailCode,
            Long patientId,
            String patientName,
            String departmentName,
            String performerId,
            String performerName,
            String resultManagerId,
            String resultManagerName,
            String resultSummary,
            LocalDateTime judgedAt,
            LocalDateTime confirmedAt,
            String readerId,
            String diagnosisName,
            String status,
            LocalDateTime createdAt
    ) {
        this.pathologyExamResultId = pathologyExamResultId;
        this.pathologyExamId = pathologyExamId;
        this.testExecutionId = testExecutionId;
        this.detailCode = detailCode;
        this.patientId = patientId;
        this.patientName = patientName;
        this.departmentName = departmentName;
        this.performerId = performerId;
        this.performerName = performerName;
        this.resultManagerId = resultManagerId;
        this.resultManagerName = resultManagerName;
        this.resultSummary = resultSummary;
        this.judgedAt = judgedAt;
        this.confirmedAt = confirmedAt;
        this.readerId = readerId;
        this.diagnosisName = diagnosisName;
        this.status = status;
        this.createdAt = createdAt;
    }
}
