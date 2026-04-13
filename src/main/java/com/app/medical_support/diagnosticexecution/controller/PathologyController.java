package com.app.medical_support.diagnosticexecution.controller;

import com.app.medical_support.common.ApiResponse;
import com.app.medical_support.diagnosticexecution.dto.PathologyCreateReqDTO;
import com.app.medical_support.diagnosticexecution.dto.PathologyDTO;
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
@RequestMapping("/api/pathology")
@RequiredArgsConstructor
@Tag(name = "Pathology", description = "Pathology exam API")
public class PathologyController {

    private final DiagnosticExecutionService diagnosticExecutionService;

    @Operation(summary = "병리 검사 목록 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<List<PathologyDTO>>> findList() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Pathology list loaded.", diagnosticExecutionService.findPathologyList()));
    }

    @Operation(summary = "병리 검사 단건 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PathologyDTO>> findDetail(@PathVariable String id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Pathology detail loaded.", diagnosticExecutionService.findPathologyDetail(id)));
    }

    @Operation(summary = "병리 검사 등록")
    @PostMapping
    public ResponseEntity<ApiResponse<PathologyDTO>> register(@RequestBody PathologyCreateReqDTO pathologyDTO) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Pathology created.", diagnosticExecutionService.registerPathology(pathologyDTO)));
    }

    @Operation(summary = "병리 검사 수정")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PathologyDTO>> modify(@PathVariable String id, @RequestBody PathologyDTO pathologyDTO) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Pathology updated.", diagnosticExecutionService.modifyPathology(id, pathologyDTO)));
    }

    @Operation(summary = "병리 검사 비활성화")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> remove(@PathVariable String id) {
        diagnosticExecutionService.deletePathology(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Pathology deactivated.", id));
    }
}
