package com.app.medical_support.diagnosticresult.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImagingResultUpdateReqDTO {

    private String readingSummary;
    private String readingDetail;
    private String status;
}
