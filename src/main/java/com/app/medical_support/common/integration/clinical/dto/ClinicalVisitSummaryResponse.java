package com.app.medical_support.common.integration.clinical.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClinicalVisitSummaryResponse {

    private Long visitId;
    private Long patientId;
    private Long doctorId;
    private Long receptionId;
    private String visitStatus;
    private String startTime;
    private String endTime;
    private String createdAt;
    private String updatedAt;
}
