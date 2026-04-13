package com.app.medical_support.nursingtreatment.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecordUpdateDTO {

    private Integer systolicBp;
    private Integer diastolicBp;
    private Integer pulse;
    private Integer respiration;
    private Double temperature;
    private Integer spo2;
    private String observation;
    private Integer painScore;
    private String consciousnessLevel;
    private String initialAssessment;
    private String pastMedicalHistory;
    private String status;
    private Long receptionId;
    private String nursingId;
    private String heightCm;
    private String weightKg;
}
