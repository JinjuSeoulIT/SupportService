package com.app.medical_support.diagnosticexecution.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PhysiologicalDTO {

    private String physiologicalExamId;
    private String testExecutionId;
    private String detailCode;
    private Long patientId;
    private String patientName;
    private String departmentName;
    private String examEquipmentId;
    private String rawData;
    private String reportDocId;
    private String performerId;
    private String status;
    private String progressStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
