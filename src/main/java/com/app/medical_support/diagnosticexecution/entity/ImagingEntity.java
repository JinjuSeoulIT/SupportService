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
@Table(schema = "CHJ", name = "IMAGING_EXAM")
@Getter
@Setter
@NoArgsConstructor
public class ImagingEntity {

    @Id
    @Column(name = "IMAGING_EXAM_ID")
    private String imagingExamId;

    @Column(name = "VISIT_ID")
    private String visitId;

    @Column(name = "IMAGING_TYPE")
    private String imagingType;

    @Column(name = "EXAM_STATUS_YN")
    private String examStatusYn;

    @Column(name = "EXAM_AT")
    private LocalDateTime examAt;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;
}
