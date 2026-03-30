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
    @Column(name = "TREATMENT_RESULT_ID")
    private String procedureResultId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PERFORMED_AT")
    private String performedAt;

    @Column(name = "PERFORMER_ID")
    private String performerId;

    @Column(name = "DETAIL")
    private String detail;
}
