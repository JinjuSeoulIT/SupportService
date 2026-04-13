package com.app.medical_support.diagnosticexecution.controller;

import com.app.medical_support.common.ApiResponse;
import com.app.medical_support.diagnosticexecution.dto.PhysiologicalCreateReqDTO;
import com.app.medical_support.diagnosticexecution.dto.PhysiologicalDTO;
import com.app.medical_support.diagnosticexecution.service.DiagnosticExecutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/physiological")
@RequiredArgsConstructor
@Tag(name = "Physiological", description = "Physiological exam API")
public class PhysiologicalController {

    private final DiagnosticExecutionService diagnosticExecutionService;

    @Operation(summary = "생리 기능 검사 목록 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<List<PhysiologicalDTO>>> findList() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Physiological list loaded.", diagnosticExecutionService.findPhysiologicalList()));
    }

    @Operation(summary = "생리 기능 검사 단건 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PhysiologicalDTO>> findDetail(@PathVariable String id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Physiological detail loaded.", diagnosticExecutionService.findPhysiologicalDetail(id)));
    }

    @Operation(summary = "생리 기능 검사 등록")
    @PostMapping
    public ResponseEntity<ApiResponse<PhysiologicalDTO>> register(@RequestBody PhysiologicalCreateReqDTO physiologicalDTO) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Physiological created.", diagnosticExecutionService.registerPhysiological(physiologicalDTO)));
    }

    @Operation(summary = "생리 기능 검사 수정")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PhysiologicalDTO>> modify(@PathVariable String id, @RequestBody PhysiologicalDTO physiologicalDTO) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Physiological updated.", diagnosticExecutionService.modifyPhysiological(id, physiologicalDTO)));
    }

    @Operation(summary = "생리 기능 검사 비활성화")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> remove(@PathVariable String id) {
        diagnosticExecutionService.deletePhysiological(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Physiological deactivated.", id));
    }
}
