package com.app.medical_support.nursingtreatment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TreatmentResultReqDTO {


    private String status;
    private String nursingId;
    private String detail;
    private Long patientId;
    private String patientName;
    private String departmentName;
    private String procedureResultId;
    private String nurseName;

}
