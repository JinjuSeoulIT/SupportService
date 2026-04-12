package com.app.medical_support.diagnosticresult.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ImagingResultCreateReqDTO {

    private String imagingResultId;
    private String imagingExamId;
    private String readingSummary;
    private String readingDetail;
    private LocalDateTime confirmedAt;
    private String resultManagerId;
    private String resultManagerName;
    private String status;
    private String imagingType;
}
