package com.app.medical_support.diagnosticresult.service;

import com.app.medical_support.diagnosticresult.dto.EndoscopyResultDTO;
import com.app.medical_support.diagnosticresult.dto.ImagingResultDTO;
import com.app.medical_support.diagnosticresult.dto.PathologyResultDTO;
import com.app.medical_support.diagnosticresult.dto.PhysiologicalResultDTO;
import com.app.medical_support.diagnosticresult.dto.SpecimenTestResultDTO;
import com.app.medical_support.diagnosticresult.dto.TestResultListDTO;
import com.app.medical_support.diagnosticresult.dto.TestResultSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestResultFacadeServiceImpl implements TestResultFacadeService {

    private static final String TYPE_IMAGING = "IMAGING";
    private static final String TYPE_SPECIMEN = "SPECIMEN";
    private static final String TYPE_PATHOLOGY = "PATHOLOGY";
    private static final String TYPE_ENDOSCOPY = "ENDOSCOPY";
    private static final String TYPE_PHYSIOLOGICAL = "PHYSIOLOGICAL";
    private static final String STATUS_INACTIVE = "INACTIVE";

    private final DiagnosticResultService diagnosticResultService;

    @Override
    public List<TestResultListDTO> findTestResultList(TestResultSearchCondition condition) {
        TestResultSearchCondition searchCondition = condition != null ? condition : new TestResultSearchCondition();

        List<TestResultListDTO> mergedResults = new ArrayList<>();
        mergedResults.addAll(mapImagingResults(diagnosticResultService.findImagingResultList()));
        mergedResults.addAll(mapSpecimenResults(diagnosticResultService.findSpecimenResultList()));
        mergedResults.addAll(mapPathologyResults(diagnosticResultService.findPathologyResultList()));
        mergedResults.addAll(mapEndoscopyResults(diagnosticResultService.findEndoscopyResultList()));
        mergedResults.addAll(mapPhysiologicalResults(diagnosticResultService.findPhysiologicalResultList()));

        return mergedResults.stream()
                .filter(result -> matchesIncludeInactive(result, searchCondition))
                .filter(result -> matchesEquals(result.getResultType(), searchCondition.getResultType()))
                .filter(result -> matchesContains(result.getPatientName(), searchCondition.getPatientName()))
                .filter(result -> matchesContains(result.getDetailCode(), searchCondition.getDetailCode()))
                .filter(result -> matchesContains(result.getDepartmentName(), searchCondition.getDepartmentName()))
                .filter(result -> matchesEquals(result.getStatus(), searchCondition.getStatus()))
                .filter(result -> matchesDateRange(result.getResultAt(), searchCondition))
                .sorted(Comparator
                        .comparing(TestResultListDTO::getResultAt, Comparator.nullsLast(Comparator.reverseOrder()))
                        .thenComparing(TestResultListDTO::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder()))
                        .thenComparing(TestResultListDTO::getResultId, Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
    }

    private List<TestResultListDTO> mapImagingResults(List<ImagingResultDTO> source) {
        return source.stream().map(dto -> {
            TestResultListDTO result = createCommonResult(TYPE_IMAGING, dto.getImagingResultId(), dto.getImagingExamId());
            result.setTestExecutionId(dto.getTestExecutionId());
            result.setDetailCode(dto.getDetailCode());
            result.setPatientId(dto.getPatientId());
            result.setPatientName(dto.getPatientName());
            result.setDepartmentName(dto.getDepartmentName());
            result.setPerformerId(dto.getPerformerId());
            result.setPerformerName(dto.getPerformerName());
            result.setSummary(dto.getReadingSummary());
            result.setResultAt(dto.getConfirmedAt());
            result.setStatus(dto.getStatus());
            result.setCreatedAt(dto.getCreatedAt());
            return result;
        }).toList();
    }

    private List<TestResultListDTO> mapSpecimenResults(List<SpecimenTestResultDTO> source) {
        return source.stream().map(dto -> {
            TestResultListDTO result = createCommonResult(TYPE_SPECIMEN, dto.getSpecimenExamResultId(), dto.getSpecimenExamId());
            result.setTestExecutionId(dto.getTestExecutionId());
            result.setDetailCode(dto.getDetailCode());
            result.setPatientId(dto.getPatientId());
            result.setPatientName(dto.getPatientName());
            result.setDepartmentName(dto.getDepartmentName());
            result.setPerformerId(dto.getPerformerId());
            result.setPerformerName(dto.getPerformerName());
            result.setSummary(dto.getResultValue());
            result.setResultAt(dto.getConfirmedAt());
            result.setStatus(dto.getStatus());
            result.setCreatedAt(dto.getCreatedAt());
            return result;
        }).toList();
    }

    private List<TestResultListDTO> mapPathologyResults(List<PathologyResultDTO> source) {
        return source.stream().map(dto -> {
            TestResultListDTO result = createCommonResult(TYPE_PATHOLOGY, dto.getPathologyExamResultId(), dto.getPathologyExamId());
            result.setTestExecutionId(dto.getTestExecutionId());
            result.setDetailCode(dto.getDetailCode());
            result.setPatientId(dto.getPatientId());
            result.setPatientName(dto.getPatientName());
            result.setDepartmentName(dto.getDepartmentName());
            result.setPerformerId(dto.getPerformerId());
            result.setPerformerName(dto.getPerformerName());
            result.setSummary(dto.getResultSummary());
            result.setResultAt(dto.getConfirmedAt());
            result.setStatus(dto.getStatus());
            result.setCreatedAt(dto.getCreatedAt());
            return result;
        }).toList();
    }

    private List<TestResultListDTO> mapEndoscopyResults(List<EndoscopyResultDTO> source) {
        return source.stream().map(dto -> {
            TestResultListDTO result = createCommonResult(TYPE_ENDOSCOPY, dto.getEndoscopyResultId(), dto.getEndoscopyExamId());
            result.setTestExecutionId(dto.getTestExecutionId());
            result.setDetailCode(dto.getDetailCode());
            result.setPatientId(dto.getPatientId());
            result.setPatientName(dto.getPatientName());
            result.setDepartmentName(dto.getDepartmentName());
            result.setPerformerId(dto.getPerformerId());
            result.setPerformerName(dto.getPerformerName());
            result.setSummary(dto.getFinding());
            result.setResultAt(dto.getConfirmedAt());
            result.setStatus(dto.getStatus());
            result.setCreatedAt(dto.getCreatedAt());
            return result;
        }).toList();
    }

    private List<TestResultListDTO> mapPhysiologicalResults(List<PhysiologicalResultDTO> source) {
        return source.stream().map(dto -> {
            TestResultListDTO result = createCommonResult(TYPE_PHYSIOLOGICAL, dto.getPhysiologicalExamResultId(), dto.getPhysiologicalExamId());
            result.setTestExecutionId(dto.getTestExecutionId());
            result.setDetailCode(dto.getDetailCode());
            result.setPatientId(dto.getPatientId());
            result.setPatientName(dto.getPatientName());
            result.setDepartmentName(dto.getDepartmentName());
            result.setPerformerId(dto.getPerformerId());
            result.setPerformerName(dto.getPerformerName());
            result.setSummary(dto.getResultValue());
            result.setResultAt(dto.getConfirmedAt());
            result.setStatus(dto.getStatus());
            result.setCreatedAt(dto.getCreatedAt());
            return result;
        }).toList();
    }

    private TestResultListDTO createCommonResult(String resultType, String resultId, String examId) {
        TestResultListDTO result = new TestResultListDTO();
        result.setResultType(resultType);
        result.setResultTypeName(toResultTypeName(resultType));
        result.setResultId(resultId);
        result.setExamId(examId);
        return result;
    }

    private boolean matchesIncludeInactive(TestResultListDTO result, TestResultSearchCondition condition) {
        boolean includeInactive = Boolean.TRUE.equals(condition.getIncludeInactive());
        return includeInactive || !STATUS_INACTIVE.equalsIgnoreCase(trimToEmpty(result.getStatus()));
    }

    private boolean matchesEquals(String source, String keyword) {
        if (!hasText(keyword)) {
            return true;
        }
        return trimToEmpty(source).equalsIgnoreCase(keyword.trim());
    }

    private boolean matchesContains(String source, String keyword) {
        if (!hasText(keyword)) {
            return true;
        }
        return trimToEmpty(source).toLowerCase().contains(keyword.trim().toLowerCase());
    }

    private boolean matchesDateRange(LocalDateTime resultAt, TestResultSearchCondition condition) {
        if (condition.getStartDate() == null && condition.getEndDate() == null) {
            return true;
        }
        if (resultAt == null) {
            return false;
        }

        LocalDateTime startDateTime = condition.getStartDate() != null ? condition.getStartDate().atStartOfDay() : null;
        LocalDateTime endDateTime = condition.getEndDate() != null ? condition.getEndDate().atTime(LocalTime.MAX) : null;

        if (startDateTime != null && resultAt.isBefore(startDateTime)) {
            return false;
        }
        if (endDateTime != null && resultAt.isAfter(endDateTime)) {
            return false;
        }
        return true;
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private String trimToEmpty(String value) {
        return value == null ? "" : value.trim();
    }

    private String toResultTypeName(String resultType) {
        return switch (resultType) {
            case TYPE_IMAGING -> "영상검사";
            case TYPE_SPECIMEN -> "검체검사";
            case TYPE_PATHOLOGY -> "병리검사";
            case TYPE_ENDOSCOPY -> "내시경검사";
            case TYPE_PHYSIOLOGICAL -> "생리기능검사";
            default -> resultType;
        };
    }
}
