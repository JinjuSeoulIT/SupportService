package com.app.medical_support.diagnosticresult.repository;

import com.app.medical_support.diagnosticresult.entity.PathologyResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PathologyResultRepository extends JpaRepository<PathologyResultEntity, String> {
    boolean existsByPathologyExamId(String pathologyExamId);
}
