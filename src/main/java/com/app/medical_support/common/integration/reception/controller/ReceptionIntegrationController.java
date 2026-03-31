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

    @Operation(summary = "접수 대기 환자 목록 조회")
    @GetMapping("/waiting")
    public ResponseEntity<ApiResponse<List<OutpatientReceptionDTO>>> findWaitingList(
            @Parameter(description = "Department ID")
            @RequestParam(required = false) Long departmentId,
            @Parameter(description = "Doctor ID")
            @RequestParam(required = false) Long doctorId,
            @Parameter(description = "Date (YYYY-MM-DD)")
            @RequestParam(required = false) String date
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Reception waiting list loaded.",
                        receptionIntegrationService.findWaitingList(departmentId, doctorId, date)
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
