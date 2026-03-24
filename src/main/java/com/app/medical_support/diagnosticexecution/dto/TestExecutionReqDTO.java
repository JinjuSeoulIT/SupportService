package com.app.medical_support.diagnosticexecution.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "진료 검사 수행 수정 정보")
@Getter
@Setter
@NoArgsConstructor
public class TestExecutionReqDTO {

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
}
