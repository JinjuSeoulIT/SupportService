package com.app.medical_support.diagnosticexecution.controller;

import com.app.medical_support.common.ApiResponse;
import com.app.medical_support.diagnosticexecution.dto.ImagingDTO;
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
@RequestMapping("/api/imaging")
@RequiredArgsConstructor
@Tag(name = "Imaging", description = "Imaging exam API")
public class ImagingController {

    private final DiagnosticExecutionService diagnosticExecutionService;

    @Operation(summary = "영상 검사 목록 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ImagingDTO>>> findList() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Imaging list loaded.", diagnosticExecutionService.findImagingList()));
    }

    @Operation(summary = "영상 검사 단건 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ImagingDTO>> findDetail(@PathVariable String id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Imaging detail loaded.", diagnosticExecutionService.findImagingDetail(id)));
    }

    @Operation(summary = "영상 검사 등록")
    @PostMapping
    public ResponseEntity<ApiResponse<ImagingDTO>> register(@RequestBody ImagingDTO imagingDTO) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Imaging created.", diagnosticExecutionService.registerImaging(imagingDTO)));
    }

    @Operation(summary = "영상 검사 수정")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ImagingDTO>> modify(@PathVariable String id, @RequestBody ImagingDTO imagingDTO) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Imaging updated.", diagnosticExecutionService.modifyImaging(id, imagingDTO)));
    }

    @Operation(summary = "영상 검사 비활성화")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> remove(@PathVariable String id) {
        diagnosticExecutionService.deleteImaging(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Imaging deactivated.", id));
    }
}
