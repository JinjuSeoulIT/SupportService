package com.app.medical_support.nursingtreatment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TreatmentResultDTO {

    private String procedureResultId;
    private String orderItemId;
    private String status;
    private String performedAt;
    private String performerId;
    private String detail;
}
