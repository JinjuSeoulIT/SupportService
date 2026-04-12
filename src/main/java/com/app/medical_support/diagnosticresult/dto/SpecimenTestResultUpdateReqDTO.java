package com.app.medical_support.diagnosticresult.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SpecimenTestResultUpdateReqDTO {

    private String resultItemCode;
    private String resultValue;
    private String unit;
    private String referenceRange;
    private String judgement;
    private LocalDateTime confirmedAt;
    private String resultManagerId;
    private String resultManagerName;
    private String status;
}
