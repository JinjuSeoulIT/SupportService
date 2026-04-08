package com.app.medical_support.nursingtreatment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TreatmentResultDTO {

    private String treatmentResultId;
    private String procedureResultId;
    private String status;
    private String createdAt;
    private String nursingId;
    private String detail;
    private Long patientId;
    private String patientName;
    private String departmentName;
}
