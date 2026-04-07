package com.app.medical_support.nursingtreatment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "CHJ", name = "TREATMENT_RESULT")
@Getter
@Setter
@NoArgsConstructor
public class TreatmentResultEntity {

    @Id
    @Column(name = "PROCEDURE_RESULT_ID")
    private String procedureResultId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATED_AT")
    private String createdAt;

    @Column(name = "NURSING_ID")
    private String nursingId;

    @Column(name = "DETAIL")
    private String detail;

    @Column(name = "PATIENT_ID")
    private Long patientId;

    @Column(name = "PATIENT_NAME")
    private String patientName;

    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;
}
