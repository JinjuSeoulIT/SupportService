package com.app.medical_support.nursingtreatment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MedicationRecordUpdateDTO {


    private String medicationRecordId;
    private String administeredAt;
    private Double doseNumber;
    private String doseUnit;
    private String doseKind;
    private String nursingId;
    private String nurseName;
    private String status;
    private Long patientId;
    private String patientName;
    private String departmentName;
    private String medicationId;
}
