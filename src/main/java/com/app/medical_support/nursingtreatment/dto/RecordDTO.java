package com.app.medical_support.nursingtreatment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Schema(description = "간호 기록 내용")
@Getter
@Setter
@NoArgsConstructor
public class RecordDTO {

    @Id
    @Schema(description = "간호 기록 아이디")
    private String recordId;

    @Schema(description = "기록 일시")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime recordedAt;

    @Schema(description = "수축기 혈압")
    private Integer systolicBp;

    @Schema(description = "이완기 혈압")
    private Integer diastolicBp;

    @Schema(description = "맥박")
    private Integer pulse;

    @Schema(description = "호흡수")
    private Integer respiration;

    @Schema(description = "체온")
    private Double temperature;

    @Schema(description = "산소포화도")
    private Integer spo2;

    @Schema(description = "간호 관찰 내용(현재 상태: ex)복통, 어지러움 없음")
    private String observation;

    @Schema(description = "통증 점수")
    private Integer painScore;

    @Schema(description = "의식 수준")
    private String consciousnessLevel;

    @Schema(description = "초기 문진 요약 (과거력) ex)고혈압 과거력, 당뇨 약 복용중, 3일 전부터 기침 지속")
    private String initialAssessment;

    @Schema(description = "상태")
    private String status;

    @Schema(description = "생성 일시")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")  //년도 월 일만
    private LocalDateTime createdAt;

    @Schema(description = "수정 일시")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    @Schema(description = "진료 아이디")
    private String visitId;

    @Schema(description = "간호사 아이디")
    private String nursingId;

    @Schema(description = "키")
    private String height;

    @Schema(description = "몸무게")
    private String weight;




}
