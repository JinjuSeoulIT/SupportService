package com.app.medical_support.diagnosticresult.repository;

import com.app.medical_support.diagnosticresult.entity.ImagingResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagingResultRepository extends JpaRepository<ImagingResultEntity, String> {
    boolean existsByImagingExamId(String imagingExamId);
}
