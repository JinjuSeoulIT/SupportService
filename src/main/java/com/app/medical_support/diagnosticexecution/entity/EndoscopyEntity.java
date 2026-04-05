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
@Table(schema = "CHJ", name = "ENDOSCOPY_EXAM")
@Getter
@Setter
@NoArgsConstructor
public class EndoscopyEntity {

    @Id
    @Column(name = "ENDOSCOPY_EXAM_ID")
    private String endoscopyExamId;

    @Column(name = "TEST_EXECUTION_ID")
    private String testExecutionId;

    @Column(name = "PROCEDURE_ROOM")
    private String procedureRoom;

    @Column(name = "EQUIPMENT")
    private String equipment;

    @Column(name = "SEDATION_YN")
    private String sedationYn;

    @Column(name = "PERFORMER_ID")
    private String performerId;

    @Column(name = "PROCEDURE_AT")
    private LocalDateTime procedureAt;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PROGRESS_STATUS")
    private String progressStatus;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;
}
