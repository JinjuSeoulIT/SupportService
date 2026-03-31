package com.app.medical_support.common.integration.reception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OutpatientReceptionDTO {

    private Long receptionId;
    private Long patientId;
    private String patientName;

    private String departmentName;
    private String doctorName;

    private String scheduledAt;
    private String arrivedAt;
    private String status;

//    private String receptionNo;
//    private Long departmentId;
//    private Long doctorId;
//    private String visitType;
//    private Long reservationId;
//    private String note;
//    private Boolean isActive;
//    private String inactiveAt;
//    private String inactiveReasonCode;
//    private String inactiveReasonText;
//    private String cancelReasonCode;
//    private String cancelReasonText;
//    private String holdReasonCode;
//    private String holdReasonText;
//    private String createdBy;
//    private String updatedBy;
//    private String createdAt;
//    private String updatedAt;
}
