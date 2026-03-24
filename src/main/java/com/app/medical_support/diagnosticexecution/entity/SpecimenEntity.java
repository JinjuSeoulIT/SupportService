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
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "CHJ", name = "SPECIMEN_EXAM")
public class SpecimenEntity {

    @Id
    @Column(name = "SPECIMEN_EXAM_ID")
    private String specimenExamId;

    @Column(name = "TEST_EXECUTION_ID")
    private String testExecutionId;

    @Column(name = "SPECIMEN_TYPE")
    private String specimenType;

    @Column(name = "SPECIMEN_STATUS")
    private String specimenStatus;

    @Column(name = "COLLECTED_AT")
    private LocalDateTime collectedAt;

    @Column(name = "COLLECTED_BY_ID")
    private String collectedById;

    @Column(name = "COLLECTION_SITE")
    private String collectionSite;

    @Column(name = "RECOLLECTION_YN")
    private String recollectionYn;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;
}
