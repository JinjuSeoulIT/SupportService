package com.app.medical_support.diagnosticresult.controller;

import com.app.medical_support.common.ApiResponse;
import com.app.medical_support.diagnosticresult.dto.TestResultDetailDTO;
import com.app.medical_support.diagnosticresult.dto.TestResultListDTO;
import com.app.medical_support.diagnosticresult.dto.TestResultSearchCondition;
import com.app.medical_support.diagnosticresult.dto.TestResultUpdateReqDTO;
import com.app.medical_support.diagnosticresult.service.TestResultFacadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/testResult")
@RequiredArgsConstructor
@Tag(name = "TestResult", description = "Integrated test result API")
public class TestResultController {

    private final TestResultFacadeService testResultFacadeService;

    @Operation(summary = "Integrated test result list")
    @GetMapping
    public ResponseEntity<ApiResponse<List<TestResultListDTO>>> findList(@ModelAttribute TestResultSearchCondition condition) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Integrated test result list loaded.",
                testResultFacadeService.findTestResultList(condition)
        ));
    }

    @Operation(summary = "Integrated test result detail")
    @GetMapping("/{resultType}/{resultId}")
    public ResponseEntity<ApiResponse<TestResultDetailDTO>> findDetail(
            @PathVariable String resultType,
            @PathVariable String resultId
    ) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Integrated test result detail loaded.",
                testResultFacadeService.findTestResultDetail(resultType, resultId)
        ));
    }

    @Operation(summary = "Integrated test result update")
    @PutMapping("/{resultType}/{resultId}")
    public ResponseEntity<ApiResponse<TestResultDetailDTO>> modify(
            @PathVariable String resultType,
            @PathVariable String resultId,
            @RequestBody TestResultUpdateReqDTO dto
    ) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Integrated test result updated.",
                testResultFacadeService.modifyTestResult(resultType, resultId, dto)
        ));
    }
}
