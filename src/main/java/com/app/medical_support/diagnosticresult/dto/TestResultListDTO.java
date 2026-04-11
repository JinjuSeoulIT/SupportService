package com.app.medical_support.diagnosticresult.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TestResultListDTO {

    private String resultType;
    private String resultTypeName;
    private String resultId;
    private String examId;
    private String testExecutionId;
    private String detailCode;
    private Long patientId;
    private String patientName;
    private String departmentName;
    private String performerId;
    private String performerName;
    private String summary;
    private LocalDateTime resultAt;
    private String status;
    private LocalDateTime createdAt;
}
