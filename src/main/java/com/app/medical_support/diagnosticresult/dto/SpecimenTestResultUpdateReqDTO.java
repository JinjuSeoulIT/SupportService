package com.app.medical_support.diagnosticresult.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpecimenTestResultUpdateReqDTO {

    private String resultItemCode;
    private String resultValue;
    private String unit;
    private String referenceRange;
    private String judgement;
    private String status;
}
