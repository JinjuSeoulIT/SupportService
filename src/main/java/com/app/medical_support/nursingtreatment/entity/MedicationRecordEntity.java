package com.app.medical_support.nursingtreatment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "CHJ", name = "MEDICATION")
@Getter
@Setter
@NoArgsConstructor
public class MedicationRecordEntity {

    @Id
    @Column(name = "MEDICATION_ID")
    private String medicationId;

    @Column(name = "ORDER_ITEM_ID")
    private String orderItemId;

    @Column(name = "ADMINISTERED_AT")
    private String administeredAt;

    @Column(name = "DOSE_NUMBER")
    private Double doseNumber;

    @Column(name = "DOSE_UNIT")
    private String doseUnit;

    @Column(name = "NURSE_ID")
    private String nurseId;

    @Column(name = "STATUS")
    private String status;
}
