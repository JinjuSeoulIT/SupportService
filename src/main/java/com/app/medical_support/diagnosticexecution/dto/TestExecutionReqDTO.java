package com.app.medical_support.diagnosticexecution.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Test execution request")
@Getter
@Setter
@NoArgsConstructor
public class TestExecutionReqDTO {

    @Schema(description = "Progress status")
    private String progressStatus;

    @Schema(description = "Retry count")
    private Integer retryNo;

    @Schema(description = "Detail Code")
    private String detailCode;

    @Schema(description = "Patient ID")
    private Long patientId;

    @Schema(description = "Patient name")
    private String patientName;

    @Schema(description = "Department name")
    private String departmentName;

    @Schema(description = "Order item ID")
    private Long orderItemId;

    @Schema(description = "Execution type")
    private String executionType;

    @Schema(description = "Performer ID")
    private String performerId;

}
