//package com.app.medical_support.diagnosticresult.service;
//
//import com.app.medical_support.diagnosticresult.dto.EndoscopyResultDTO;
//import com.app.medical_support.diagnosticresult.dto.EndoscopyResultUpdateReqDTO;
//import com.app.medical_support.diagnosticresult.dto.ImagingResultDTO;
//import com.app.medical_support.diagnosticresult.dto.ImagingResultUpdateReqDTO;
//import com.app.medical_support.diagnosticresult.dto.PathologyResultDTO;
//import com.app.medical_support.diagnosticresult.dto.PathologyResultUpdateReqDTO;
//import com.app.medical_support.diagnosticresult.dto.PhysiologicalResultDTO;
//import com.app.medical_support.diagnosticresult.dto.PhysiologicalResultUpdateReqDTO;
//import com.app.medical_support.diagnosticresult.dto.SpecimenTestResultDTO;
//import com.app.medical_support.diagnosticresult.dto.SpecimenTestResultUpdateReqDTO;
//import com.app.medical_support.diagnosticresult.dto.TestResultDetailDTO;
//import com.app.medical_support.diagnosticresult.dto.TestResultListDTO;
//import com.app.medical_support.diagnosticresult.dto.TestResultSearchCondition;
//import com.app.medical_support.diagnosticresult.dto.TestResultUpdateDetailDTO;
//import com.app.medical_support.diagnosticresult.dto.TestResultUpdateReqDTO;
//import com.app.medical_support.diagnosticresult.exception.DiagnosticResultNotFoundException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class TestResultFacadeServiceImpl implements TestResultFacadeService {
//
//    private static final String TYPE_IMAGING = "IMAGING";
//    private static final String TYPE_SPECIMEN = "SPECIMEN";
//    private static final String TYPE_PATHOLOGY = "PATHOLOGY";
//    private static final String TYPE_ENDOSCOPY = "ENDOSCOPY";
//    private static final String TYPE_PHYSIOLOGICAL = "PHYSIOLOGICAL";
//    private static final String STATUS_INACTIVE = "INACTIVE";
//
//    private final DiagnosticResultService diagnosticResultService;
//
//    @Override
//    public List<TestResultListDTO> findTestResultList(TestResultSearchCondition condition) {
//        TestResultSearchCondition searchCondition = condition != null ? condition : new TestResultSearchCondition();
//
//        List<TestResultListDTO> mergedResults = new ArrayList<>();
//        mergedResults.addAll(mapImagingResults(diagnosticResultService.findImagingResultList()));
//        mergedResults.addAll(mapSpecimenResults(diagnosticResultService.findSpecimenResultList()));
//        mergedResults.addAll(mapPathologyResults(diagnosticResultService.findPathologyResultList()));
//        mergedResults.addAll(mapEndoscopyResults(diagnosticResultService.findEndoscopyResultList()));
//        mergedResults.addAll(mapPhysiologicalResults(diagnosticResultService.findPhysiologicalResultList()));
//
//        return mergedResults.stream()
//                .filter(result -> matchesIncludeInactive(result, searchCondition))
//                .filter(result -> matchesEquals(result.getResultType(), searchCondition.getResultType()))
//                .filter(result -> matchesContains(result.getPatientName(), searchCondition.getPatientName()))
//                .filter(result -> matchesContains(result.getDetailCode(), searchCondition.getDetailCode()))
//                .filter(result -> matchesContains(result.getDepartmentName(), searchCondition.getDepartmentName()))
//                .filter(result -> matchesEquals(result.getStatus(), searchCondition.getStatus()))
//                .filter(result -> matchesDateRange(result.getResultAt(), searchCondition))
//                .sorted(Comparator
//                        .comparing(TestResultListDTO::getResultAt, Comparator.nullsLast(Comparator.reverseOrder()))
//                        .thenComparing(TestResultListDTO::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder()))
//                        .thenComparing(TestResultListDTO::getResultId, Comparator.nullsLast(Comparator.reverseOrder())))
//                .toList();
//    }
//
//    @Override
//    public TestResultDetailDTO findTestResultDetail(String resultType, String resultId) {
//        String normalizedType = normalizeResultType(resultType);
//
//        return switch (normalizedType) {
//            case TYPE_IMAGING -> mapImagingDetail(diagnosticResultService.findImagingResultDetail(resultId));
//            case TYPE_SPECIMEN -> mapSpecimenDetail(diagnosticResultService.findSpecimenResultDetail(resultId));
//            case TYPE_PATHOLOGY -> mapPathologyDetail(diagnosticResultService.findPathologyResultDetail(resultId));
//            case TYPE_ENDOSCOPY -> mapEndoscopyDetail(diagnosticResultService.findEndoscopyResultDetail(resultId));
//            case TYPE_PHYSIOLOGICAL -> mapPhysiologicalDetail(diagnosticResultService.findPhysiologicalResultDetail(resultId));
//            default -> throw new DiagnosticResultNotFoundException("Unsupported result type. resultType=" + resultType);
//        };
//    }
//
//    @Override
//    public TestResultDetailDTO modifyTestResult(String resultType, String resultId, TestResultUpdateReqDTO dto) {
//        String normalizedType = normalizeResultType(resultType);
//        TestResultUpdateReqDTO request = dto != null ? dto : new TestResultUpdateReqDTO();
//        TestResultUpdateDetailDTO detail = request.getDetail() != null ? request.getDetail() : new TestResultUpdateDetailDTO();
//
//        return switch (normalizedType) {
//            case TYPE_IMAGING -> mapImagingDetail(
//                    diagnosticResultService.modifyImagingResult(resultId, toImagingUpdateReq(request, detail))
//            );
//            case TYPE_SPECIMEN -> mapSpecimenDetail(
//                    diagnosticResultService.modifySpecimenResult(resultId, toSpecimenUpdateReq(request, detail))
//            );
//            case TYPE_PATHOLOGY -> mapPathologyDetail(
//                    diagnosticResultService.modifyPathologyResult(resultId, toPathologyUpdateReq(request, detail))
//            );
//            case TYPE_ENDOSCOPY -> mapEndoscopyDetail(
//                    diagnosticResultService.modifyEndoscopyResult(resultId, toEndoscopyUpdateReq(request, detail))
//            );
//            case TYPE_PHYSIOLOGICAL -> mapPhysiologicalDetail(
//                    diagnosticResultService.modifyPhysiologicalResult(resultId, toPhysiologicalUpdateReq(request, detail))
//            );
//            default -> throw new DiagnosticResultNotFoundException("Unsupported result type. resultType=" + resultType);
//        };
//    }
//
//    private List<TestResultListDTO> mapImagingResults(List<ImagingResultDTO> source) {
//        return source.stream().map(dto -> {
//            TestResultListDTO result = createCommonResult(TYPE_IMAGING, dto.getImagingResultId(), dto.getImagingExamId());
//            result.setTestExecutionId(dto.getTestExecutionId());
//            result.setDetailCode(dto.getDetailCode());
//            result.setPatientId(dto.getPatientId());
//            result.setPatientName(dto.getPatientName());
//            result.setDepartmentName(dto.getDepartmentName());
//            result.setPerformerId(dto.getPerformerId());
//            result.setPerformerName(dto.getPerformerName());
//            result.setResultManagerId(dto.getResultManagerId());
//            result.setResultManagerName(dto.getResultManagerName());
//            result.setSummary(dto.getResultSummary());
//            result.setResultAt(dto.getConfirmedAt());
//            result.setStatus(dto.getStatus());
//            result.setCreatedAt(dto.getCreatedAt());
//            return result;
//        }).toList();
//    }
//
//    private List<TestResultListDTO> mapSpecimenResults(List<SpecimenTestResultDTO> source) {
//        return source.stream().map(dto -> {
//            TestResultListDTO result = createCommonResult(TYPE_SPECIMEN, dto.getSpecimenExamResultId(), dto.getSpecimenExamId());
//            result.setTestExecutionId(dto.getTestExecutionId());
//            result.setDetailCode(dto.getDetailCode());
//            result.setPatientId(dto.getPatientId());
//            result.setPatientName(dto.getPatientName());
//            result.setDepartmentName(dto.getDepartmentName());
//            result.setPerformerId(dto.getPerformerId());
//            result.setPerformerName(dto.getPerformerName());
//            result.setResultManagerId(dto.getResultManagerId());
//            result.setResultManagerName(dto.getResultManagerName());
//            result.setSummary(dto.getResultSummary());
//            result.setResultAt(dto.getConfirmedAt());
//            result.setStatus(dto.getStatus());
//            result.setCreatedAt(dto.getCreatedAt());
//            return result;
//        }).toList();
//    }
//
//    private List<TestResultListDTO> mapPathologyResults(List<PathologyResultDTO> source) {
//        return source.stream().map(dto -> {
//            TestResultListDTO result = createCommonResult(TYPE_PATHOLOGY, dto.getPathologyExamResultId(), dto.getPathologyExamId());
//            result.setTestExecutionId(dto.getTestExecutionId());
//            result.setDetailCode(dto.getDetailCode());
//            result.setPatientId(dto.getPatientId());
//            result.setPatientName(dto.getPatientName());
//            result.setDepartmentName(dto.getDepartmentName());
//            result.setPerformerId(dto.getPerformerId());
//            result.setPerformerName(dto.getPerformerName());
//            result.setResultManagerId(dto.getResultManagerId());
//            result.setResultManagerName(dto.getResultManagerName());
//            result.setSummary(dto.getResultSummary());
//            result.setResultAt(dto.getConfirmedAt());
//            result.setStatus(dto.getStatus());
//            result.setCreatedAt(dto.getCreatedAt());
//            return result;
//        }).toList();
//    }
//
//    private List<TestResultListDTO> mapEndoscopyResults(List<EndoscopyResultDTO> source) {
//        return source.stream().map(dto -> {
//            TestResultListDTO result = createCommonResult(TYPE_ENDOSCOPY, dto.getEndoscopyResultId(), dto.getEndoscopyExamId());
//            result.setTestExecutionId(dto.getTestExecutionId());
//            result.setDetailCode(dto.getDetailCode());
//            result.setPatientId(dto.getPatientId());
//            result.setPatientName(dto.getPatientName());
//            result.setDepartmentName(dto.getDepartmentName());
//            result.setPerformerId(dto.getPerformerId());
//            result.setPerformerName(dto.getPerformerName());
//            result.setResultManagerId(dto.getResultManagerId());
//            result.setResultManagerName(dto.getResultManagerName());
//            result.setSummary(dto.getResultSummary());
//            result.setResultAt(dto.getConfirmedAt());
//            result.setStatus(dto.getStatus());
//            result.setCreatedAt(dto.getCreatedAt());
//            return result;
//        }).toList();
//    }
//
//    private List<TestResultListDTO> mapPhysiologicalResults(List<PhysiologicalResultDTO> source) {
//        return source.stream().map(dto -> {
//            TestResultListDTO result = createCommonResult(TYPE_PHYSIOLOGICAL, dto.getPhysiologicalExamResultId(), dto.getPhysiologicalExamId());
//            result.setTestExecutionId(dto.getTestExecutionId());
//            result.setDetailCode(dto.getDetailCode());
//            result.setPatientId(dto.getPatientId());
//            result.setPatientName(dto.getPatientName());
//            result.setDepartmentName(dto.getDepartmentName());
//            result.setPerformerId(dto.getPerformerId());
//            result.setPerformerName(dto.getPerformerName());
//            result.setResultManagerId(dto.getResultManagerId());
//            result.setResultManagerName(dto.getResultManagerName());
//            result.setSummary(dto.getResultSummary());
//            result.setResultAt(dto.getConfirmedAt());
//            result.setStatus(dto.getStatus());
//            result.setCreatedAt(dto.getCreatedAt());
//            return result;
//        }).toList();
//    }
//
//    private TestResultDetailDTO mapImagingDetail(ImagingResultDTO dto) {
//        TestResultDetailDTO result = createCommonDetail(TYPE_IMAGING, dto.getImagingResultId(), dto.getImagingExamId());
//        result.setTestExecutionId(dto.getTestExecutionId());
//        result.setDetailCode(dto.getDetailCode());
//        result.setPatientId(dto.getPatientId());
//        result.setPatientName(dto.getPatientName());
//        result.setDepartmentName(dto.getDepartmentName());
//        result.setPerformerId(dto.getPerformerId());
//        result.setPerformerName(dto.getPerformerName());
//        result.setResultManagerId(dto.getResultManagerId());
//        result.setResultManagerName(dto.getResultManagerName());
//        result.setSummary(dto.getResultSummary());
//        result.setResultAt(dto.getConfirmedAt());
//        result.setStatus(dto.getStatus());
//        result.setCreatedAt(dto.getCreatedAt());
//
//        Map<String, Object> detail = new LinkedHashMap<>();
//        detail.put("readingDetail", dto.getReadingDetail());
//        result.setDetail(detail);
//        return result;
//    }
//
//    private TestResultDetailDTO mapSpecimenDetail(SpecimenTestResultDTO dto) {
//        TestResultDetailDTO result = createCommonDetail(TYPE_SPECIMEN, dto.getSpecimenExamResultId(), dto.getSpecimenExamId());
//        result.setTestExecutionId(dto.getTestExecutionId());
//        result.setDetailCode(dto.getDetailCode());
//        result.setPatientId(dto.getPatientId());
//        result.setPatientName(dto.getPatientName());
//        result.setDepartmentName(dto.getDepartmentName());
//        result.setPerformerId(dto.getPerformerId());
//        result.setPerformerName(dto.getPerformerName());
//        result.setResultManagerId(dto.getResultManagerId());
//        result.setResultManagerName(dto.getResultManagerName());
//        result.setSummary(dto.getResultSummary());
//        result.setResultAt(dto.getConfirmedAt());
//        result.setStatus(dto.getStatus());
//        result.setCreatedAt(dto.getCreatedAt());
//
//        Map<String, Object> detail = new LinkedHashMap<>();
//        detail.put("resultItemCode", dto.getResultItemCode());
//        detail.put("unit", dto.getUnit());
//        detail.put("referenceRange", dto.getReferenceRange());
//        detail.put("judgement", dto.getJudgement());
//        result.setDetail(detail);
//        return result;
//    }
//
//    private TestResultDetailDTO mapPathologyDetail(PathologyResultDTO dto) {
//        TestResultDetailDTO result = createCommonDetail(TYPE_PATHOLOGY, dto.getPathologyExamResultId(), dto.getPathologyExamId());
//        result.setTestExecutionId(dto.getTestExecutionId());
//        result.setDetailCode(dto.getDetailCode());
//        result.setPatientId(dto.getPatientId());
//        result.setPatientName(dto.getPatientName());
//        result.setDepartmentName(dto.getDepartmentName());
//        result.setPerformerId(dto.getPerformerId());
//        result.setPerformerName(dto.getPerformerName());
//        result.setResultManagerId(dto.getResultManagerId());
//        result.setResultManagerName(dto.getResultManagerName());
//        result.setSummary(dto.getResultSummary());
//        result.setResultAt(dto.getConfirmedAt());
//        result.setStatus(dto.getStatus());
//        result.setCreatedAt(dto.getCreatedAt());
//
//        Map<String, Object> detail = new LinkedHashMap<>();
//        detail.put("judgedAt", dto.getJudgedAt());
//        detail.put("readerId", dto.getReaderId());
//        detail.put("diagnosisName", dto.getDiagnosisName());
//        result.setDetail(detail);
//        return result;
//    }
//
//    private TestResultDetailDTO mapEndoscopyDetail(EndoscopyResultDTO dto) {
//        TestResultDetailDTO result = createCommonDetail(TYPE_ENDOSCOPY, dto.getEndoscopyResultId(), dto.getEndoscopyExamId());
//        result.setTestExecutionId(dto.getTestExecutionId());
//        result.setDetailCode(dto.getDetailCode());
//        result.setPatientId(dto.getPatientId());
//        result.setPatientName(dto.getPatientName());
//        result.setDepartmentName(dto.getDepartmentName());
//        result.setPerformerId(dto.getPerformerId());
//        result.setPerformerName(dto.getPerformerName());
//        result.setResultManagerId(dto.getResultManagerId());
//        result.setResultManagerName(dto.getResultManagerName());
//        result.setSummary(dto.getResultSummary());
//        result.setResultAt(dto.getConfirmedAt());
//        result.setStatus(dto.getStatus());
//        result.setCreatedAt(dto.getCreatedAt());
//
//        Map<String, Object> detail = new LinkedHashMap<>();
//        detail.put("biopsyYn", dto.getBiopsyYn());
//        detail.put("readerId", dto.getReaderId());
//        result.setDetail(detail);
//        return result;
//    }
//
//    private TestResultDetailDTO mapPhysiologicalDetail(PhysiologicalResultDTO dto) {
//        TestResultDetailDTO result = createCommonDetail(TYPE_PHYSIOLOGICAL, dto.getPhysiologicalExamResultId(), dto.getPhysiologicalExamId());
//        result.setTestExecutionId(dto.getTestExecutionId());
//        result.setDetailCode(dto.getDetailCode());
//        result.setPatientId(dto.getPatientId());
//        result.setPatientName(dto.getPatientName());
//        result.setDepartmentName(dto.getDepartmentName());
//        result.setPerformerId(dto.getPerformerId());
//        result.setPerformerName(dto.getPerformerName());
//        result.setResultManagerId(dto.getResultManagerId());
//        result.setResultManagerName(dto.getResultManagerName());
//        result.setSummary(dto.getResultSummary());
//        result.setResultAt(dto.getConfirmedAt());
//        result.setStatus(dto.getStatus());
//        result.setCreatedAt(dto.getCreatedAt());
//
//        Map<String, Object> detail = new LinkedHashMap<>();
//        detail.put("report", dto.getReport());
//        detail.put("measuredItemCode", dto.getMeasuredItemCode());
//        result.setDetail(detail);
//        return result;
//    }
//
//    private TestResultListDTO createCommonResult(String resultType, String resultId, String examId) {
//        TestResultListDTO result = new TestResultListDTO();
//        result.setResultType(resultType);
//        result.setResultTypeName(toResultTypeName(resultType));
//        result.setResultId(resultId);
//        result.setExamId(examId);
//        return result;
//    }
//
//    private ImagingResultUpdateReqDTO toImagingUpdateReq(TestResultUpdateReqDTO request, TestResultUpdateDetailDTO detail) {
//        ImagingResultUpdateReqDTO dto = new ImagingResultUpdateReqDTO();
//        dto.setResultSummary(detail.getResultSummary());
//        dto.setReadingDetail(detail.getReadingDetail());
//        dto.setConfirmedAt(request.getConfirmedAt());
//        dto.setResultManagerId(request.getResultManagerId());
//        dto.setResultManagerName(request.getResultManagerName());
//        dto.setStatus(request.getStatus());
//        return dto;
//    }
//
//    private SpecimenTestResultUpdateReqDTO toSpecimenUpdateReq(TestResultUpdateReqDTO request, TestResultUpdateDetailDTO detail) {
//        SpecimenTestResultUpdateReqDTO dto = new SpecimenTestResultUpdateReqDTO();
//        dto.setResultItemCode(detail.getResultItemCode());
//        dto.setResultSummary(detail.getResultSummary());
//        dto.setUnit(detail.getUnit());
//        dto.setReferenceRange(detail.getReferenceRange());
//        dto.setJudgement(detail.getJudgement());
//        dto.setConfirmedAt(request.getConfirmedAt());
//        dto.setResultManagerId(request.getResultManagerId());
//        dto.setResultManagerName(request.getResultManagerName());
//        dto.setStatus(request.getStatus());
//        return dto;
//    }
//
//    private PathologyResultUpdateReqDTO toPathologyUpdateReq(TestResultUpdateReqDTO request, TestResultUpdateDetailDTO detail) {
//        PathologyResultUpdateReqDTO dto = new PathologyResultUpdateReqDTO();
//        dto.setResultSummary(detail.getResultSummary());
//        dto.setJudgedAt(detail.getJudgedAt());
//        dto.setConfirmedAt(request.getConfirmedAt());
//        dto.setResultManagerId(request.getResultManagerId());
//        dto.setResultManagerName(request.getResultManagerName());
//        dto.setReaderId(detail.getReaderId());
//        dto.setDiagnosisName(detail.getDiagnosisName());
//        dto.setStatus(request.getStatus());
//        return dto;
//    }
//
//    private EndoscopyResultUpdateReqDTO toEndoscopyUpdateReq(TestResultUpdateReqDTO request, TestResultUpdateDetailDTO detail) {
//        EndoscopyResultUpdateReqDTO dto = new EndoscopyResultUpdateReqDTO();
//        dto.setResultSummary(detail.getResultSummary());
//        dto.setBiopsyYn(detail.getBiopsyYn());
//        dto.setConfirmedAt(request.getConfirmedAt());
//        dto.setResultManagerId(request.getResultManagerId());
//        dto.setResultManagerName(request.getResultManagerName());
//        dto.setReaderId(detail.getReaderId());
//        dto.setStatus(request.getStatus());
//        return dto;
//    }
//
//    private PhysiologicalResultUpdateReqDTO toPhysiologicalUpdateReq(TestResultUpdateReqDTO request, TestResultUpdateDetailDTO detail) {
//        PhysiologicalResultUpdateReqDTO dto = new PhysiologicalResultUpdateReqDTO();
//        dto.setResultSummary(detail.getResultSummary());
//        dto.setReport(detail.getReport());
//        dto.setMeasuredItemCode(detail.getMeasuredItemCode());
//        dto.setConfirmedAt(request.getConfirmedAt());
//        dto.setResultManagerId(request.getResultManagerId());
//        dto.setResultManagerName(request.getResultManagerName());
//        dto.setStatus(request.getStatus());
//        return dto;
//    }
//
//    private TestResultDetailDTO createCommonDetail(String resultType, String resultId, String examId) {
//        TestResultDetailDTO result = new TestResultDetailDTO();
//        result.setResultType(resultType);
//        result.setResultTypeName(toResultTypeName(resultType));
//        result.setResultId(resultId);
//        result.setExamId(examId);
//        return result;
//    }
//
//    private boolean matchesIncludeInactive(TestResultListDTO result, TestResultSearchCondition condition) {
//        boolean includeInactive = Boolean.TRUE.equals(condition.getIncludeInactive());
//        return includeInactive || !STATUS_INACTIVE.equalsIgnoreCase(trimToEmpty(result.getStatus()));
//    }
//
//    private boolean matchesEquals(String source, String keyword) {
//        if (!hasText(keyword)) {
//            return true;
//        }
//        return trimToEmpty(source).equalsIgnoreCase(keyword.trim());
//    }
//
//    private boolean matchesContains(String source, String keyword) {
//        if (!hasText(keyword)) {
//            return true;
//        }
//        return trimToEmpty(source).toLowerCase().contains(keyword.trim().toLowerCase());
//    }
//
//    private boolean matchesDateRange(LocalDateTime resultAt, TestResultSearchCondition condition) {
//        if (condition.getStartDate() == null && condition.getEndDate() == null) {
//            return true;
//        }
//        if (resultAt == null) {
//            return false;
//        }
//
//        LocalDateTime startDateTime = condition.getStartDate() != null ? condition.getStartDate().atStartOfDay() : null;
//        LocalDateTime endDateTime = condition.getEndDate() != null ? condition.getEndDate().atTime(LocalTime.MAX) : null;
//
//        if (startDateTime != null && resultAt.isBefore(startDateTime)) {
//            return false;
//        }
//        if (endDateTime != null && resultAt.isAfter(endDateTime)) {
//            return false;
//        }
//        return true;
//    }
//
//    private boolean hasText(String value) {
//        return value != null && !value.trim().isEmpty();
//    }
//
//    private String trimToEmpty(String value) {
//        return value == null ? "" : value.trim();
//    }
//
//    private String normalizeResultType(String value) {
//        return hasText(value) ? value.trim().toUpperCase() : "";
//    }
//
//    private String toResultTypeName(String resultType) {
//        return switch (resultType) {
//            case TYPE_IMAGING -> "영상검사";
//            case TYPE_SPECIMEN -> "검체검사";
//            case TYPE_PATHOLOGY -> "병리검사";
//            case TYPE_ENDOSCOPY -> "내시경검사";
//            case TYPE_PHYSIOLOGICAL -> "생리기능검사";
//            default -> resultType;
//        };
//    }
//}
