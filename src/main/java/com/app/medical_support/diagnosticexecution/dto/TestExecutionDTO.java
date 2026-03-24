package com.app.medical_support.diagnosticexecution.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "진료 검사 수행 정보")
@Getter
@Setter
@NoArgsConstructor
public class TestExecutionDTO {

    @Schema(description = "검사수행ID")
    private String testExecutionId;

    @Schema(description = "오더항목ID")
    private Long orderItemId;

    @Schema(description = "검사수행유형")
    private String executionType;

    @Schema(description = "진행상태")
    private String progressStatus;

    @Schema(description = "재시도횟수")
    private Integer retryNo;

    @Schema(description = "시작일시")
    private LocalDateTime startedAt;

    @Schema(description = "완료일시")
    private LocalDateTime completedAt;

    @Schema(description = "수행자ID")
    private Long performerId;

    @Schema(description = "수정일시")
    private LocalDateTime updatedAt;
}
