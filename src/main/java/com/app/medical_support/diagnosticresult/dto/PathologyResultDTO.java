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
    private String resultSummary;
    private LocalDateTime judgedAt;
    private LocalDateTime confirmedAt;
    private String readerId;
    private String diagnosisName;
    private String status;
    private LocalDateTime createdAt;
}
