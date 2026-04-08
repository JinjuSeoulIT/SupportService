package com.app.medical_support.nursingtreatment.controller;

import com.app.medical_support.common.ApiResponse;
import com.app.medical_support.nursingtreatment.dto.MedicationRecordDTO;
import com.app.medical_support.nursingtreatment.dto.MedicationRecordReqDTO;
import com.app.medical_support.nursingtreatment.dto.RecordStatusRequest;
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
@RequestMapping("/api/medicationRecord")
@RequiredArgsConstructor
@Tag(name = "MedicationRecord", description = "Medication record API")
public class MedicationRecordController {

    private final NursingTreatmentService nursingTreatmentService;

    @Operation(summary = "투약 기록 목록 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<List<MedicationRecordDTO>>> findList() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Medication record list loaded.", nursingTreatmentService.findMedicationRecordList()));
    }

    @Operation(summary = "투약 기록 단건 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MedicationRecordDTO>> findDetail(@PathVariable String id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Medication record detail loaded.", nursingTreatmentService.findMedicationRecordDetail(id)));
    }

    @Operation(summary = "투약 기록 등록")
    @PostMapping
    public ResponseEntity<ApiResponse<MedicationRecordDTO>> register(@RequestBody MedicationRecordReqDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Medication record created.", nursingTreatmentService.registerMedicationRecord(dto)));
    }

    @Operation(summary = "투약 기록 수정")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MedicationRecordDTO>> modify(@PathVariable String id, @RequestBody MedicationRecordReqDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Medication record updated.", nursingTreatmentService.modifyMedicationRecord(id, dto)));
    }

    @Operation(summary = "투약 기록 상태 변경")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<MedicationRecordDTO>> updateStatus(@PathVariable String id, @RequestBody RecordStatusRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Medication record status updated.", nursingTreatmentService.updateMedicationRecordStatus(id, request.getStatus())));
    }
}
