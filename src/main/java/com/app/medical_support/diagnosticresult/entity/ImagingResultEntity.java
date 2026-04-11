package com.app.medical_support.diagnosticresult.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(schema = "CHJ", name = "IMAGING_EXAM_RESULT")
@Getter
@Setter
@NoArgsConstructor
public class ImagingResultEntity {

    @Id
    @Column(name = "IMAGING_RESULT_ID")
    private String imagingResultId;

    @Column(name = "IMAGING_EXAM_ID")
    private String imagingExamId;

    @Column(name = "READING_SUMMARY")
    private String readingSummary;

    @Lob
    @Column(name = "READING_DETAIL")
    private String readingDetail;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
}
