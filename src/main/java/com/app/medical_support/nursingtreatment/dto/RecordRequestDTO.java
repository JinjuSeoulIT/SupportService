package com.app.medical_support.nursingtreatment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RecordRequestDTO {

    private String recordId;
    private String visitId;
    private LocalDateTime recordedAt;
    private String status;
    private String observation;
    private Integer systolicBp;
    private Integer diastolicBp;
    private Integer pulse;
    private Double temperature;
    private Integer spo2;
    private String nursingId;
    private String nurseName;
    private String departmentName;
    private String shiftType;
    private String receptionId;
    private String consciousnessLevel;
    private String initialAssessment;
    private String updatedAt;
    private String patientName;
    private String heightCm;
    private String weightKg;


}
