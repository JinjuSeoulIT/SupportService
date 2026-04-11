package com.app.medical_support.diagnosticresult.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SpecimenTestResultDTO {

    private String specimenExamResultId;
    private String specimenExamId;
    private String testExecutionId;
    private String detailCode;
    private Long patientId;
    private String patientName;
    private String departmentName;
    private String performerId;
    private String performerName;
    private String resultItemCode;
    private String resultValue;
    private String unit;
    private String referenceRange;
    private String judgement;
    private String status;
    private LocalDateTime createdAt;

    public SpecimenTestResultDTO(
            String specimenExamResultId,
            String specimenExamId,
            String testExecutionId,
            String detailCode,
            Long patientId,
            String patientName,
            String departmentName,
            String performerId,
            String performerName,
            String resultItemCode,
            String resultValue,
            String unit,
            String referenceRange,
            String judgement,
            String status,
            LocalDateTime createdAt
    ) {
        this.specimenExamResultId = specimenExamResultId;
        this.specimenExamId = specimenExamId;
        this.testExecutionId = testExecutionId;
        this.detailCode = detailCode;
        this.patientId = patientId;
        this.patientName = patientName;
        this.departmentName = departmentName;
        this.performerId = performerId;
        this.performerName = performerName;
        this.resultItemCode = resultItemCode;
        this.resultValue = resultValue;
        this.unit = unit;
        this.referenceRange = referenceRange;
        this.judgement = judgement;
        this.status = status;
        this.createdAt = createdAt;
    }
}
