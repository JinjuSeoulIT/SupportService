package com.app.medical_support.diagnosticresult.service;

import com.app.medical_support.diagnosticresult.dto.EndoscopyResultDTO;
import com.app.medical_support.diagnosticresult.dto.ImagingResultDTO;
import com.app.medical_support.diagnosticresult.dto.PathologyResultDTO;
import com.app.medical_support.diagnosticresult.dto.PhysiologicalResultDTO;
import com.app.medical_support.diagnosticresult.dto.SpecimenTestResultDTO;
import com.app.medical_support.diagnosticresult.entity.EndoscopyResultEntity;
import com.app.medical_support.diagnosticresult.entity.ImagingResultEntity;
import com.app.medical_support.diagnosticresult.entity.PathologyResultEntity;
import com.app.medical_support.diagnosticresult.entity.PhysiologicalResultEntity;
import com.app.medical_support.diagnosticresult.entity.SpecimenTestResultEntity;
import com.app.medical_support.diagnosticresult.exception.DiagnosticResultNotFoundException;
import com.app.medical_support.diagnosticresult.repository.EndoscopyResultRepository;
import com.app.medical_support.diagnosticresult.repository.ImagingResultRepository;
import com.app.medical_support.diagnosticresult.repository.PathologyResultRepository;
import com.app.medical_support.diagnosticresult.repository.PhysiologicalResultRepository;
import com.app.medical_support.diagnosticresult.repository.SpecimenTestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiagnosticResultServiceImpl implements DiagnosticResultService {

    private final ImagingResultRepository imagingResultRepository;
    private final EndoscopyResultRepository endoscopyResultRepository;
    private final PathologyResultRepository pathologyResultRepository;
    private final PhysiologicalResultRepository physiologicalResultRepository;
    private final SpecimenTestResultRepository specimenTestResultRepository;

    @Override
    public List<ImagingResultDTO> findImagingResultList() {
        return imagingResultRepository.findAll().stream().map(this::toImagingResultDTO).toList();
    }

    @Override
    public ImagingResultDTO findImagingResultDetail(String id) {
        return toImagingResultDTO(imagingResultRepository.findById(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Imaging result not found. id=" + id)));
    }

    @Override
    @Transactional
    public ImagingResultDTO registerImagingResult(ImagingResultDTO dto) {
        ImagingResultEntity entity = new ImagingResultEntity();
        entity.setImagingResultId(hasText(dto.getImagingResultId()) ? dto.getImagingResultId() : createImagingResultId());
        entity.setImagingExamId(dto.getImagingExamId());
        entity.setReadingSummary(dto.getReadingSummary());
        entity.setReadingDetail(dto.getReadingDetail());
        entity.setStatus(normalizeStatus(dto.getStatus()));
        entity.setCreatedAt(LocalDateTime.now());
        return toImagingResultDTO(imagingResultRepository.save(entity));
    }

    @Override
    @Transactional
    public ImagingResultDTO modifyImagingResult(String id, ImagingResultDTO dto) {
        ImagingResultEntity entity = imagingResultRepository.findById(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Imaging result not found. id=" + id));
        entity.setImagingExamId(dto.getImagingExamId());
        entity.setReadingSummary(dto.getReadingSummary());
        entity.setReadingDetail(dto.getReadingDetail());
        entity.setStatus(normalizeStatus(dto.getStatus()));
        return toImagingResultDTO(imagingResultRepository.save(entity));
    }

    @Override
    @Transactional
    public void deleteImagingResult(String id) {
        ImagingResultEntity entity = imagingResultRepository.findById(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Imaging result not found. id=" + id));
        entity.setStatus("INACTIVE");
        imagingResultRepository.save(entity);
    }

    @Override
    public List<EndoscopyResultDTO> findEndoscopyResultList() {
        return endoscopyResultRepository.findAll().stream().map(this::toEndoscopyResultDTO).toList();
    }

    @Override
    public EndoscopyResultDTO findEndoscopyResultDetail(String id) {
        return toEndoscopyResultDTO(endoscopyResultRepository.findById(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Endoscopy result not found. id=" + id)));
    }

    @Override
    @Transactional
    public EndoscopyResultDTO registerEndoscopyResult(EndoscopyResultDTO dto) {
        EndoscopyResultEntity entity = new EndoscopyResultEntity();
        entity.setEndoscopyResultId(hasText(dto.getEndoscopyResultId()) ? dto.getEndoscopyResultId() : createEndoscopyResultId());
        entity.setEndoscopyExamId(dto.getEndoscopyExamId());
        entity.setFinding(dto.getFinding());
        entity.setBiopsyYn(normalizeYnFlag(dto.getBiopsyYn()));
        entity.setConfirmedAt(dto.getConfirmedAt());
        entity.setReaderId(dto.getReaderId());
        entity.setStatus(normalizeStatus(dto.getStatus()));
        entity.setCreatedAt(LocalDateTime.now());
        return toEndoscopyResultDTO(endoscopyResultRepository.save(entity));
    }

    @Override
    @Transactional
    public EndoscopyResultDTO modifyEndoscopyResult(String id, EndoscopyResultDTO dto) {
        EndoscopyResultEntity entity = endoscopyResultRepository.findById(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Endoscopy result not found. id=" + id));
        entity.setEndoscopyExamId(dto.getEndoscopyExamId());
        entity.setFinding(dto.getFinding());
        entity.setBiopsyYn(normalizeYnFlag(dto.getBiopsyYn()));
        entity.setConfirmedAt(dto.getConfirmedAt());
        entity.setReaderId(dto.getReaderId());
        entity.setStatus(normalizeStatus(dto.getStatus()));
        return toEndoscopyResultDTO(endoscopyResultRepository.save(entity));
    }

    @Override
    @Transactional
    public void deleteEndoscopyResult(String id) {
        EndoscopyResultEntity entity = endoscopyResultRepository.findById(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Endoscopy result not found. id=" + id));
        entity.setStatus("INACTIVE");
        endoscopyResultRepository.save(entity);
    }

    @Override
    public List<PathologyResultDTO> findPathologyResultList() {
        return pathologyResultRepository.findAll().stream().map(this::toPathologyResultDTO).toList();
    }

    @Override
    public PathologyResultDTO findPathologyResultDetail(String id) {
        return toPathologyResultDTO(pathologyResultRepository.findById(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Pathology result not found. id=" + id)));
    }

    @Override
    @Transactional
    public PathologyResultDTO registerPathologyResult(PathologyResultDTO dto) {
        PathologyResultEntity entity = new PathologyResultEntity();
        entity.setPathologyExamResultId(hasText(dto.getPathologyExamResultId()) ? dto.getPathologyExamResultId() : createPathologyResultId());
        entity.setPathologyExamId(dto.getPathologyExamId());
        entity.setResultSummary(dto.getResultSummary());
        entity.setJudgedAt(dto.getJudgedAt());
        entity.setConfirmedAt(dto.getConfirmedAt());
        entity.setReaderId(dto.getReaderId());
        entity.setDiagnosisName(dto.getDiagnosisName());
        entity.setStatus(normalizeStatus(dto.getStatus()));
        entity.setCreatedAt(LocalDateTime.now());
        return toPathologyResultDTO(pathologyResultRepository.save(entity));
    }

    @Override
    @Transactional
    public PathologyResultDTO modifyPathologyResult(String id, PathologyResultDTO dto) {
        PathologyResultEntity entity = pathologyResultRepository.findById(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Pathology result not found. id=" + id));
        entity.setPathologyExamId(dto.getPathologyExamId());
        entity.setResultSummary(dto.getResultSummary());
        entity.setJudgedAt(dto.getJudgedAt());
        entity.setConfirmedAt(dto.getConfirmedAt());
        entity.setReaderId(dto.getReaderId());
        entity.setDiagnosisName(dto.getDiagnosisName());
        entity.setStatus(normalizeStatus(dto.getStatus()));
        return toPathologyResultDTO(pathologyResultRepository.save(entity));
    }

    @Override
    @Transactional
    public void deletePathologyResult(String id) {
        PathologyResultEntity entity = pathologyResultRepository.findById(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Pathology result not found. id=" + id));
        entity.setStatus("INACTIVE");
        pathologyResultRepository.save(entity);
    }

    @Override
    public List<PhysiologicalResultDTO> findPhysiologicalResultList() {
        return physiologicalResultRepository.findAll().stream().map(this::toPhysiologicalResultDTO).toList();
    }

    @Override
    public PhysiologicalResultDTO findPhysiologicalResultDetail(String id) {
        return toPhysiologicalResultDTO(physiologicalResultRepository.findById(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Physiological result not found. id=" + id)));
    }

    @Override
    @Transactional
    public PhysiologicalResultDTO registerPhysiologicalResult(PhysiologicalResultDTO dto) {
        PhysiologicalResultEntity entity = new PhysiologicalResultEntity();
        entity.setPhysiologicalExamResultId(hasText(dto.getPhysiologicalExamResultId()) ? dto.getPhysiologicalExamResultId() : createPhysiologicalResultId());
        entity.setPhysiologicalExamId(dto.getPhysiologicalExamId());
        entity.setResultValue(dto.getResultValue());
        entity.setReport(dto.getReport());
        entity.setMeasuredItemCode(dto.getMeasuredItemCode());
        entity.setStatus(normalizeStatus(dto.getStatus()));
        entity.setCreatedAt(LocalDateTime.now());
        return toPhysiologicalResultDTO(physiologicalResultRepository.save(entity));
    }

    @Override
    @Transactional
    public PhysiologicalResultDTO modifyPhysiologicalResult(String id, PhysiologicalResultDTO dto) {
        PhysiologicalResultEntity entity = physiologicalResultRepository.findById(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Physiological result not found. id=" + id));
        entity.setPhysiologicalExamId(dto.getPhysiologicalExamId());
        entity.setResultValue(dto.getResultValue());
        entity.setReport(dto.getReport());
        entity.setMeasuredItemCode(dto.getMeasuredItemCode());
        entity.setStatus(normalizeStatus(dto.getStatus()));
        return toPhysiologicalResultDTO(physiologicalResultRepository.save(entity));
    }

    @Override
    @Transactional
    public void deletePhysiologicalResult(String id) {
        PhysiologicalResultEntity entity = physiologicalResultRepository.findById(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Physiological result not found. id=" + id));
        entity.setStatus("INACTIVE");
        physiologicalResultRepository.save(entity);
    }

    @Override
    public List<SpecimenTestResultDTO> findSpecimenResultList() {
        return specimenTestResultRepository.findAll().stream().map(this::toSpecimenResultDTO).toList();
    }

    @Override
    public SpecimenTestResultDTO findSpecimenResultDetail(String id) {
        return toSpecimenResultDTO(specimenTestResultRepository.findById(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Specimen result not found. id=" + id)));
    }

    @Override
    @Transactional
    public SpecimenTestResultDTO registerSpecimenResult(SpecimenTestResultDTO dto) {
        SpecimenTestResultEntity entity = new SpecimenTestResultEntity();
        entity.setSpecimenExamResultId(hasText(dto.getSpecimenExamResultId()) ? dto.getSpecimenExamResultId() : createSpecimenResultId());
        entity.setSpecimenExamId(dto.getSpecimenExamId());
        entity.setResultItemCode(dto.getResultItemCode());
        entity.setResultValue(dto.getResultValue());
        entity.setUnit(dto.getUnit());
        entity.setReferenceRange(dto.getReferenceRange());
        entity.setJudgement(dto.getJudgement());
        entity.setStatus(normalizeStatus(dto.getStatus()));
        entity.setCreatedAt(LocalDateTime.now());
        return toSpecimenResultDTO(specimenTestResultRepository.save(entity));
    }

    @Override
    @Transactional
    public SpecimenTestResultDTO modifySpecimenResult(String id, SpecimenTestResultDTO dto) {
        SpecimenTestResultEntity entity = specimenTestResultRepository.findById(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Specimen result not found. id=" + id));
        entity.setSpecimenExamId(dto.getSpecimenExamId());
        entity.setResultItemCode(dto.getResultItemCode());
        entity.setResultValue(dto.getResultValue());
        entity.setUnit(dto.getUnit());
        entity.setReferenceRange(dto.getReferenceRange());
        entity.setJudgement(dto.getJudgement());
        entity.setStatus(normalizeStatus(dto.getStatus()));
        return toSpecimenResultDTO(specimenTestResultRepository.save(entity));
    }

    @Override
    @Transactional
    public void deleteSpecimenResult(String id) {
        SpecimenTestResultEntity entity = specimenTestResultRepository.findById(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Specimen result not found. id=" + id));
        entity.setStatus("INACTIVE");
        specimenTestResultRepository.save(entity);
    }

    private ImagingResultDTO toImagingResultDTO(ImagingResultEntity entity) {
        ImagingResultDTO dto = new ImagingResultDTO();
        dto.setImagingResultId(entity.getImagingResultId());
        dto.setImagingExamId(entity.getImagingExamId());
        dto.setReadingSummary(entity.getReadingSummary());
        dto.setReadingDetail(entity.getReadingDetail());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    private EndoscopyResultDTO toEndoscopyResultDTO(EndoscopyResultEntity entity) {
        EndoscopyResultDTO dto = new EndoscopyResultDTO();
        dto.setEndoscopyResultId(entity.getEndoscopyResultId());
        dto.setEndoscopyExamId(entity.getEndoscopyExamId());
        dto.setFinding(entity.getFinding());
        dto.setBiopsyYn(entity.getBiopsyYn());
        dto.setConfirmedAt(entity.getConfirmedAt());
        dto.setReaderId(entity.getReaderId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    private PathologyResultDTO toPathologyResultDTO(PathologyResultEntity entity) {
        PathologyResultDTO dto = new PathologyResultDTO();
        dto.setPathologyExamResultId(entity.getPathologyExamResultId());
        dto.setPathologyExamId(entity.getPathologyExamId());
        dto.setResultSummary(entity.getResultSummary());
        dto.setJudgedAt(entity.getJudgedAt());
        dto.setConfirmedAt(entity.getConfirmedAt());
        dto.setReaderId(entity.getReaderId());
        dto.setDiagnosisName(entity.getDiagnosisName());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    private PhysiologicalResultDTO toPhysiologicalResultDTO(PhysiologicalResultEntity entity) {
        PhysiologicalResultDTO dto = new PhysiologicalResultDTO();
        dto.setPhysiologicalExamResultId(entity.getPhysiologicalExamResultId());
        dto.setPhysiologicalExamId(entity.getPhysiologicalExamId());
        dto.setResultValue(entity.getResultValue());
        dto.setReport(entity.getReport());
        dto.setMeasuredItemCode(entity.getMeasuredItemCode());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    private SpecimenTestResultDTO toSpecimenResultDTO(SpecimenTestResultEntity entity) {
        SpecimenTestResultDTO dto = new SpecimenTestResultDTO();
        dto.setSpecimenExamResultId(entity.getSpecimenExamResultId());
        dto.setSpecimenExamId(entity.getSpecimenExamId());
        dto.setResultItemCode(entity.getResultItemCode());
        dto.setResultValue(entity.getResultValue());
        dto.setUnit(entity.getUnit());
        dto.setReferenceRange(entity.getReferenceRange());
        dto.setJudgement(entity.getJudgement());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private String normalizeStatus(String status) {
        if (!hasText(status)) {
            return "ACTIVE";
        }

        String trimmed = status.trim().toUpperCase();
        if ("Y".equals(trimmed)) {
            return "ACTIVE";
        }
        if ("N".equals(trimmed)) {
            return "INACTIVE";
        }

        return trimmed;
    }

    private String normalizeYnFlag(String value) {
        if (!hasText(value)) {
            return "N";
        }

        String trimmed = value.trim().toUpperCase();
        if ("Y".equals(trimmed) || "YES".equals(trimmed) || "TRUE".equals(trimmed)) {
            return "Y";
        }

        return "N";
    }

    private String createImagingResultId() {
        return "IMG_RES_" + System.currentTimeMillis();
    }

    private String createEndoscopyResultId() {
        return "ENDO_RES_" + System.currentTimeMillis();
    }

    private String createPathologyResultId() {
        return "PATH_RES_" + System.currentTimeMillis();
    }

    private String createPhysiologicalResultId() {
        return "PHYS_RES_" + System.currentTimeMillis();
    }

    private String createSpecimenResultId() {
        return "SPEC_RES_" + System.currentTimeMillis();
    }
}
