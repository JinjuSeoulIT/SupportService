package com.app.medical_support.diagnosticresult.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PhysiologicalResultDTO {

    private String physiologicalExamResultId;
    private String physiologicalExamId;
    private String testExecutionId;
    private String detailCode;
    private Long patientId;
    private String patientName;
    private String departmentName;
    private String performerId;
    private String performerName;
    private String resultValue;
    private String report;
    private String measuredItemCode;
    private String status;
    private LocalDateTime createdAt;

    public PhysiologicalResultDTO(
            String physiologicalExamResultId,
            String physiologicalExamId,
            String testExecutionId,
            String detailCode,
            Long patientId,
            String patientName,
            String departmentName,
            String performerId,
            String performerName,
            String resultValue,
            String report,
            String measuredItemCode,
            String status,
            LocalDateTime createdAt
    ) {
        this.physiologicalExamResultId = physiologicalExamResultId;
        this.physiologicalExamId = physiologicalExamId;
        this.testExecutionId = testExecutionId;
        this.detailCode = detailCode;
        this.patientId = patientId;
        this.patientName = patientName;
        this.departmentName = departmentName;
        this.performerId = performerId;
        this.performerName = performerName;
        this.resultValue = resultValue;
        this.report = report;
        this.measuredItemCode = measuredItemCode;
        this.status = status;
        this.createdAt = createdAt;
    }
}
