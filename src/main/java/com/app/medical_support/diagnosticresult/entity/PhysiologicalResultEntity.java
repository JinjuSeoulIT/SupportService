package com.app.medical_support.diagnosticresult.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(schema = "CHJ", name = "PHYSIOLOGICAL_EXAM_RESULT")
@Getter
@Setter
@NoArgsConstructor
public class PhysiologicalResultEntity {

    @Id
    @Column(name = "PHYSIOLOGICAL_EXAM_RESULT_ID")
    private String physiologicalExamResultId;

    @Column(name = "PHYSIOLOGICAL_EXAM_ID")
    private String physiologicalExamId;

    @Column(name = "RESULT_VALUE")
    private String resultValue;

    @Column(name = "REPORT")
    private String report;

    @Column(name = "MEASURED_ITEM_CODE")
    private String measuredItemCode;

    @Column(name = "CONFIRMED_AT")
    private LocalDateTime confirmedAt;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
}
