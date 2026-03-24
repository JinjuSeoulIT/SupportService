package com.app.medical_support.nursingtreatment.controller;

import com.app.medical_support.common.ApiResponse;
import com.app.medical_support.nursingtreatment.dto.RecordStatusRequest;
import com.app.medical_support.nursingtreatment.dto.TreatmentResultDTO;
import com.app.medical_support.nursingtreatment.service.NursingTreatmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/treatmentResult")
@RequiredArgsConstructor
@Tag(name = "TreatmentResult", description = "Treatment result API")
public class TreatmentResultController {

    private final NursingTreatmentService nursingTreatmentService;

    @Operation(summary = "처치 결과 목록 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<List<TreatmentResultDTO>>> findList() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Treatment result list loaded.", nursingTreatmentService.findTreatmentResultList()));
    }

    @Operation(summary = "처치 결과 단건 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TreatmentResultDTO>> findDetail(@PathVariable String id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Treatment result detail loaded.", nursingTreatmentService.findTreatmentResultDetail(id)));
    }

    @Operation(summary = "처치 결과 등록")
    @PostMapping
    public ResponseEntity<ApiResponse<TreatmentResultDTO>> register(@RequestBody TreatmentResultDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Treatment result created.", nursingTreatmentService.registerTreatmentResult(dto)));
    }

    @Operation(summary = "처치 결과 수정")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TreatmentResultDTO>> modify(@PathVariable String id, @RequestBody TreatmentResultDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Treatment result updated.", nursingTreatmentService.modifyTreatmentResult(id, dto)));
    }

    @Operation(summary = "처치 결과 상태 변경")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<TreatmentResultDTO>> updateStatus(@PathVariable String id, @RequestBody RecordStatusRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Treatment result status updated.", nursingTreatmentService.updateTreatmentResultStatus(id, request.getStatus())));
    }
}
