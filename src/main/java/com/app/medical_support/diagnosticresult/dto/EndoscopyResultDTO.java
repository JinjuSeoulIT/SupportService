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
    private String finding;
    private String biopsyYn;
    private LocalDateTime confirmedAt;
    private String readerId;
    private String status;
    private LocalDateTime createdAt;
}
