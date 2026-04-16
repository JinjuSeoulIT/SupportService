package com.app.medical_support.common.integration.clinical.service;

import com.app.medical_support.common.integration.clinical.client.ClinicalApiClient;
import com.app.medical_support.common.integration.clinical.dto.ClinicalVitalAssessResponse;
import com.app.medical_support.common.integration.clinical.dto.ClinicalVisitSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class ClinicalIntegrationService {

    private final ClinicalApiClient clinicalApiClient;

    public ClinicalVitalAssessResponse findVitalAssessByVisitId(Long visitId) {
        validatePositive("visitId", visitId);
        return clinicalApiClient.fetchVitalAssess(visitId);
    }

    public ClinicalVitalAssessResponse findVitalAssessByReceptionId(Long receptionId) {
        validatePositive("receptionId", receptionId);

        Long visitId = clinicalApiClient.fetchVisitList().stream()
                .filter(item -> receptionId.equals(item.getReceptionId()))
                .map(ClinicalVisitSummaryResponse::getVisitId)
                .filter(this::isPositive)
                .max(Comparator.naturalOrder())
                .orElse(null);

        if (visitId == null) {
            return null;
        }
        return clinicalApiClient.fetchVitalAssess(visitId);
    }

    private void validatePositive(String fieldName, Long value) {
        if (!isPositive(value)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldName + " must be a positive number.");
        }
    }

    private boolean isPositive(Long value) {
        return value != null && value > 0;
    }
}
