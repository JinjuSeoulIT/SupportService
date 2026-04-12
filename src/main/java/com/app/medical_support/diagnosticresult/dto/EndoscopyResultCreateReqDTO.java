package com.app.medical_support.diagnosticresult.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EndoscopyResultCreateReqDTO {

    private String endoscopyResultId;
    private String endoscopyExamId;
    private String finding;
    private String biopsyYn;
    private LocalDateTime confirmedAt;
    private String resultManagerId;
    private String resultManagerName;
    private String readerId;
    private String status;
}
