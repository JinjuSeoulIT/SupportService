package com.app.medical_support.diagnosticexecution.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "Test execution data")
@Getter
@Setter
@NoArgsConstructor
public class TestExecutionDTO {

    @Schema(description = "Test execution ID")
    private String testExecutionId;

    @Schema(description = "Detail Code")
    private String detailCode;

    @Schema(description = "Order item ID")
    private Long orderItemId;

    @Schema(description = "Execution type")
    private String executionType;

    @Schema(description = "Progress status")
    private String progressStatus;

    @Schema(description = "Retry count")
    private Integer retryNo;

    @Schema(description = "Started at")
    private LocalDateTime startedAt;

    @Schema(description = "Completed at")
    private LocalDateTime completedAt;

    @Schema(description = "Performer ID")
    private Long performerId;

    @Schema(description = "Patient ID")
    private Long patientId;

    @Schema(description = "Patient name")
    private String patientName;

    @Schema(description = "Department name")
    private String departmentName;

    @Schema(description = "Created at")
    private LocalDateTime createdAt;

    @Schema(description = "Updated at")
    private LocalDateTime updatedAt;
}
