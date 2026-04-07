package com.app.medical_support.nursingtreatment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MedicationRecordDTO {

    private String medicationId;
    private String administeredAt;
    private Double doseNumber;
    private String doseUnit;
    private String nursingId;
    private String status;
}
