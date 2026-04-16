package com.app.medical_support.common.integration.clinical.controller;

import com.app.medical_support.common.ApiResponse;
import com.app.medical_support.common.integration.clinical.dto.ClinicalVitalAssessResponse;
import com.app.medical_support.common.integration.clinical.service.ClinicalIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
@Tag(name = "ClinicalIntegration", description = "Clinical integration API")
public class ClinicalIntegrationController {

    private final ClinicalIntegrationService clinicalIntegrationService;

    @Operation(summary = "진료 visitId 기반 활력·문진 단건 조회")
    @GetMapping("/{visitId}/vital-assess")
    public ResponseEntity<ApiResponse<ClinicalVitalAssessResponse>> findVitalAssessByVisitId(
            @Parameter(description = "Visit ID") @PathVariable Long visitId
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Clinical vital assess loaded.",
                        clinicalIntegrationService.findVitalAssessByVisitId(visitId)
                )
        );
    }

    @Operation(summary = "접수 receptionId 기반 활력·문진 단건 조회")
    @GetMapping("/reception/{receptionId}/vital-assess")
    public ResponseEntity<ApiResponse<ClinicalVitalAssessResponse>> findVitalAssessByReceptionId(
            @Parameter(description = "Reception ID") @PathVariable Long receptionId
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Clinical vital assess loaded.",
                        clinicalIntegrationService.findVitalAssessByReceptionId(receptionId)
                )
        );
    }
}
