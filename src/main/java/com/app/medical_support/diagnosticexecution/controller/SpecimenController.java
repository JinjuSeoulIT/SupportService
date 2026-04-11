package com.app.medical_support.diagnosticexecution.controller;

import com.app.medical_support.common.ApiResponse;
import com.app.medical_support.diagnosticexecution.dto.SpecimenDTO;
import com.app.medical_support.diagnosticexecution.service.DiagnosticExecutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/specimen")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Specimen", description = "Specimen exam API")
public class SpecimenController {

    private final DiagnosticExecutionService specimenService;

    @Operation(summary = "검체 검사 검색", description = "searchType과 searchValue로 검체 검사를 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<SpecimenDTO>>> searchSpecimens(
            @Parameter(description = "testExecutionId, specimenType, specimenStatus")
            @RequestParam("searchType") String searchType,
            @Parameter(description = "Search value")
            @RequestParam("searchValue") String searchValue
    ) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Specimen search completed.", specimenService.searchSpecimen(searchType, searchValue)));
    }

    @Operation(summary = "검체 검사 목록 조회", description = "전체 검체 검사 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<SpecimenDTO>>> findList() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Specimen list loaded.", specimenService.findSpecimenList()));
    }

    @Operation(summary = "검체 검사 단건 조회", description = "검체 검사 1건을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SpecimenDTO>> findSpecimenDetail(
            @Parameter(description = "Specimen exam ID")
            @PathVariable String id
    ) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Specimen detail loaded.", specimenService.findSpecimenDetail(id)));
    }

    @Operation(summary = "검체 검사 등록", description = "새 검체 검사를 등록합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<SpecimenDTO>> registerSpecimen(
            @Parameter(description = "Specimen exam request body")
            @RequestBody SpecimenDTO specimen
    ) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Specimen created.", specimenService.registerSpecimen(specimen)));
    }

    @Operation(summary = "검체 검사 수정", description = "검체 검사 정보를 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SpecimenDTO>> modifySpecimen(
            @Parameter(description = "Specimen exam ID")
            @PathVariable String id,
            @Parameter(description = "Specimen exam request body")
            @RequestBody SpecimenDTO specimenDTO
    ) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Specimen updated.", specimenService.modifySpecimen(id, specimenDTO)));
    }

    @Operation(summary = "검체 검사 비활성화", description = "상태값을 INACTIVE로 변경합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> removeSpecimen(
            @Parameter(description = "Specimen exam ID")
            @PathVariable String id
    ) {
        specimenService.deleteSpecimen(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Specimen deactivated.", id));
    }
}
