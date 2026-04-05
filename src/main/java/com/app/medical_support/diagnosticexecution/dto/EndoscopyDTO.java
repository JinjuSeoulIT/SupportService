package com.app.medical_support.diagnosticexecution.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EndoscopyDTO {

    private String endoscopyExamId;
    private String testExecutionId;
    private String procedureRoom;
    private String equipment;
    private String sedationYn;
    private String performerId;
    private LocalDateTime procedureAt;
    private String status;
    private String progressStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
