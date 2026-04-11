package com.app.medical_support.diagnosticresult.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhysiologicalResultUpdateReqDTO {

    private String resultValue;
    private String report;
    private String measuredItemCode;
    private String status;
}
