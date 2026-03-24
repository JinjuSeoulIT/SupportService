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
    private String resultValue;
    private String report;
    private String measuredItemCode;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
