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
    private String resultItemCode;
    private String resultValue;
    private String unit;
    private String referenceRange;
    private String judgement;
    private String status;
    private LocalDateTime createdAt;
}
