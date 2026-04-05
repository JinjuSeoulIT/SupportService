package com.app.medical_support.diagnosticexecution.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(schema = "CHJ", name = "PHYSIOLOGICAL_EXAM")
@Getter
@Setter
@NoArgsConstructor
public class PhysiologicalEntity {

    @Id
    @Column(name = "PHYSIOLOGICAL_EXAM_ID")
    private String physiologicalExamId;

    @Column(name = "TEST_EXECUTION_ID")
    private String testExecutionId;

    @Column(name = "EXAM_EQUIPMENT_ID")
    private String examEquipmentId;

    @Column(name = "RAW_DATA")
    private String rawData;

    @Column(name = "REPORT_DOC_ID")
    private String reportDocId;

    @Column(name = "PERFORMER_ID")
    private String performerId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PROGRESS_STATUS")
    private String progressStatus;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;
}
