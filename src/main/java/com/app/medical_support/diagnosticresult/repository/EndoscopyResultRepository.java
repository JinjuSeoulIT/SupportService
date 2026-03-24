package com.app.medical_support.diagnosticresult.repository;

import com.app.medical_support.diagnosticresult.entity.EndoscopyResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EndoscopyResultRepository extends JpaRepository<EndoscopyResultEntity, String> {
}
