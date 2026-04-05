package com.app.medical_support.diagnosticexecution.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PathologyDTO {

    private String pathologyExamId;
    private String testExecutionId;
    private String tissueStatus;
    private String collectionMethod;
    private String tissueSite;
    private String tissueType;
    private LocalDateTime collectedAt;
    private String performerId;
    private String reexamYn;
    private String status;
    private String progressStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
