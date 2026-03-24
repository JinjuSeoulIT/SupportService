package com.app.medical_support.diagnosticresult.repository;

import com.app.medical_support.diagnosticresult.entity.SpecimenTestResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecimenTestResultRepository extends JpaRepository<SpecimenTestResultEntity, String> {
}
