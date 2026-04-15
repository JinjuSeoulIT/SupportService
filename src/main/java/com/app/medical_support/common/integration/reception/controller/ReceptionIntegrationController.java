package com.app.medical_support.common.integration.reception.controller;

import com.app.medical_support.common.ApiResponse;
import com.app.medical_support.common.integration.reception.dto.OutpatientReceptionDTO;
import com.app.medical_support.common.integration.reception.service.ReceptionIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/receptions")
@RequiredArgsConstructor
@Tag(name = "ReceptionIntegration", description = "Reception integration API")
public class ReceptionIntegrationController {

    private final ReceptionIntegrationService receptionIntegrationService;

    @Operation(summary = "접수 목록(조건) 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<List<OutpatientReceptionDTO>>> findList(
            @Parameter(description = "진료일(yyyy-MM-dd)", required = true, example = "2026-04-15")
            @RequestParam String visitDate,
            @Parameter(description = "진료유형", example = "OUTPATIENT")
            @RequestParam(required = false, defaultValue = "OUTPATIENT") String visitType,
            @Parameter(description = "상태 목록(CSV)", example = "WAITING,CALLED,IN_PROGRESS")
            @RequestParam(required = false, defaultValue = "WAITING,CALLED,IN_PROGRESS") String statuses
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Reception list loaded.",
                        receptionIntegrationService.findListByConditions(visitDate, visitType, statuses)
                )
        );
    }

    @Operation(summary = "접수 상세 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OutpatientReceptionDTO>> findDetail(
            @Parameter(description = "Reception ID")
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Reception detail loaded.",
                        receptionIntegrationService.findDetail(id)
                )
        );
    }
}
