package com.app.medical_support.diagnosticexecution.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 영상·내시경 등 모달리티 검사 행이 완료 등으로 전이될 때 다운스트림(진료·수납 등)으로 보내는 요약 페이로드.
 */
@Getter
@Setter
@NoArgsConstructor
public class DiagnosticExamOutcomeDTO {

    private String examKind;
    private String examId;
    private String testExecutionId;
    private Long orderItemId;
    private String progressStatus;
    private Long patientId;
    private String detailCode;
}
