package com.app.medical_support.diagnosticresult.repository;

import com.app.medical_support.diagnosticresult.entity.PhysiologicalResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhysiologicalResultRepository extends JpaRepository<PhysiologicalResultEntity, String> {
}
