package com.app.medical_support.diagnosticexecution.controller;

import com.app.medical_support.common.ApiResponse;
import com.app.medical_support.diagnosticexecution.dto.EndoscopyDTO;
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
@RequestMapping("/api/endoscopy")
@RequiredArgsConstructor
@Tag(name = "Endoscopy", description = "Endoscopy exam API")
public class EndoscopyController {

    private final DiagnosticExecutionService diagnosticExecutionService;

    @Operation(summary = "내시경 검사 목록 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<List<EndoscopyDTO>>> findList() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Endoscopy list loaded.", diagnosticExecutionService.findEndoscopyList()));
    }

    @Operation(summary = "내시경 검사 단건 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EndoscopyDTO>> findDetail(@PathVariable String id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Endoscopy detail loaded.", diagnosticExecutionService.findEndoscopyDetail(id)));
    }

    @Operation(summary = "내시경 검사 등록")
    @PostMapping
    public ResponseEntity<ApiResponse<EndoscopyDTO>> register(@RequestBody EndoscopyDTO endoscopyDTO) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Endoscopy created.", diagnosticExecutionService.registerEndoscopy(endoscopyDTO)));
    }

    @Operation(summary = "내시경 검사 수정")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EndoscopyDTO>> modify(@PathVariable String id, @RequestBody EndoscopyDTO endoscopyDTO) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Endoscopy updated.", diagnosticExecutionService.modifyEndoscopy(id, endoscopyDTO)));
    }

    @Operation(summary = "내시경 검사 비활성화")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> remove(@PathVariable String id) {
        diagnosticExecutionService.deleteEndoscopy(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Endoscopy deactivated.", id));
    }
}
