package com.app.medical_support.diagnosticresult.service;

import com.app.medical_support.diagnosticresult.dto.EndoscopyResultCreateReqDTO;
import com.app.medical_support.diagnosticresult.dto.EndoscopyResultDTO;
import com.app.medical_support.diagnosticresult.dto.EndoscopyResultUpdateReqDTO;
import com.app.medical_support.diagnosticresult.dto.ImagingResultCreateReqDTO;
import com.app.medical_support.diagnosticresult.dto.ImagingResultDTO;
import com.app.medical_support.diagnosticresult.dto.ImagingResultUpdateReqDTO;
import com.app.medical_support.diagnosticresult.dto.PathologyResultCreateReqDTO;
import com.app.medical_support.diagnosticresult.dto.PathologyResultDTO;
import com.app.medical_support.diagnosticresult.dto.PathologyResultUpdateReqDTO;
import com.app.medical_support.diagnosticresult.dto.PhysiologicalResultCreateReqDTO;
import com.app.medical_support.diagnosticresult.dto.PhysiologicalResultDTO;
import com.app.medical_support.diagnosticresult.dto.PhysiologicalResultUpdateReqDTO;
import com.app.medical_support.diagnosticresult.dto.SpecimenTestResultCreateReqDTO;
import com.app.medical_support.diagnosticresult.dto.SpecimenTestResultDTO;
import com.app.medical_support.diagnosticresult.dto.SpecimenTestResultUpdateReqDTO;
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
        return imagingResultRepository.findImagingResultResponseList();
    }

    @Override
    public ImagingResultDTO findImagingResultDetail(String id) {
        return getImagingResultResponse(id);
    }

    @Override
    @Transactional
    public ImagingResultDTO registerImagingResult(ImagingResultCreateReqDTO dto) {
        ImagingResultEntity entity = new ImagingResultEntity();

        entity.setImagingResultId(
                hasText(dto.getImagingResultId())
                        ? dto.getImagingResultId()
                        : createImagingResultId()
        );

        entity.setImagingExamId(dto.getImagingExamId());


        entity.setImagingType(dto.getImagingType());

        entity.setReadingSummary(dto.getReadingSummary());
        entity.setReadingDetail(dto.getReadingDetail());
        entity.setConfirmedAt(dto.getConfirmedAt());
        entity.setStatus(normalizeStatus(dto.getStatus()));
        entity.setCreatedAt(LocalDateTime.now());

        ImagingResultEntity savedEntity = imagingResultRepository.save(entity);

        return getImagingResultResponse(savedEntity.getImagingResultId());


    }

    @Override
    @Transactional
    public ImagingResultDTO modifyImagingResult(String id, ImagingResultUpdateReqDTO dto) {
        ImagingResultEntity entity = imagingResultRepository.findById(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Imaging result not found. id=" + id));
        entity.setReadingSummary(dto.getReadingSummary());
        entity.setReadingDetail(dto.getReadingDetail());
        entity.setConfirmedAt(dto.getConfirmedAt());
        entity.setStatus(normalizeStatus(dto.getStatus()));
        ImagingResultEntity savedEntity = imagingResultRepository.save(entity);
        return getImagingResultResponse(savedEntity.getImagingResultId());
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
        return endoscopyResultRepository.findEndoscopyResultResponseList();
    }

    @Override
    public EndoscopyResultDTO findEndoscopyResultDetail(String id) {
        return getEndoscopyResultResponse(id);
    }

    @Override
    @Transactional
    public EndoscopyResultDTO registerEndoscopyResult(EndoscopyResultCreateReqDTO dto) {
        EndoscopyResultEntity entity = new EndoscopyResultEntity();
        entity.setEndoscopyResultId(hasText(dto.getEndoscopyResultId()) ? dto.getEndoscopyResultId() : createEndoscopyResultId());
        entity.setEndoscopyExamId(dto.getEndoscopyExamId());
        entity.setFinding(dto.getFinding());
        entity.setBiopsyYn(normalizeYnFlag(dto.getBiopsyYn()));
        entity.setConfirmedAt(dto.getConfirmedAt());
        entity.setReaderId(dto.getReaderId());
        entity.setStatus(normalizeStatus(dto.getStatus()));
        entity.setCreatedAt(LocalDateTime.now());
        EndoscopyResultEntity savedEntity = endoscopyResultRepository.save(entity);
        return getEndoscopyResultResponse(savedEntity.getEndoscopyResultId());
    }

    @Override
    @Transactional
    public EndoscopyResultDTO modifyEndoscopyResult(String id, EndoscopyResultUpdateReqDTO dto) {
        EndoscopyResultEntity entity = endoscopyResultRepository.findById(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Endoscopy result not found. id=" + id));
        entity.setFinding(dto.getFinding());
        entity.setBiopsyYn(normalizeYnFlag(dto.getBiopsyYn()));
        entity.setConfirmedAt(dto.getConfirmedAt());
        entity.setReaderId(dto.getReaderId());
        entity.setStatus(normalizeStatus(dto.getStatus()));
        EndoscopyResultEntity savedEntity = endoscopyResultRepository.save(entity);
        return getEndoscopyResultResponse(savedEntity.getEndoscopyResultId());
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
        return pathologyResultRepository.findPathologyResultResponseList();
    }

    @Override
    public PathologyResultDTO findPathologyResultDetail(String id) {
        return getPathologyResultResponse(id);
    }

    @Override
    @Transactional
    public PathologyResultDTO registerPathologyResult(PathologyResultCreateReqDTO dto) {
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
        PathologyResultEntity savedEntity = pathologyResultRepository.save(entity);
        return getPathologyResultResponse(savedEntity.getPathologyExamResultId());
    }

    @Override
    @Transactional
    public PathologyResultDTO modifyPathologyResult(String id, PathologyResultUpdateReqDTO dto) {
        PathologyResultEntity entity = pathologyResultRepository.findById(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Pathology result not found. id=" + id));
        entity.setResultSummary(dto.getResultSummary());
        entity.setJudgedAt(dto.getJudgedAt());
        entity.setConfirmedAt(dto.getConfirmedAt());
        entity.setReaderId(dto.getReaderId());
        entity.setDiagnosisName(dto.getDiagnosisName());
        entity.setStatus(normalizeStatus(dto.getStatus()));
        PathologyResultEntity savedEntity = pathologyResultRepository.save(entity);
        return getPathologyResultResponse(savedEntity.getPathologyExamResultId());
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
        return physiologicalResultRepository.findPhysiologicalResultResponseList();
    }

    @Override
    public PhysiologicalResultDTO findPhysiologicalResultDetail(String id) {
        return getPhysiologicalResultResponse(id);
    }

    @Override
    @Transactional
    public PhysiologicalResultDTO registerPhysiologicalResult(PhysiologicalResultCreateReqDTO dto) {
        PhysiologicalResultEntity entity = new PhysiologicalResultEntity();
        entity.setPhysiologicalExamResultId(hasText(dto.getPhysiologicalExamResultId()) ? dto.getPhysiologicalExamResultId() : createPhysiologicalResultId());
        entity.setPhysiologicalExamId(dto.getPhysiologicalExamId());
        entity.setResultValue(dto.getResultValue());
        entity.setReport(dto.getReport());
        entity.setMeasuredItemCode(dto.getMeasuredItemCode());
        entity.setConfirmedAt(dto.getConfirmedAt());
        entity.setStatus(normalizeStatus(dto.getStatus()));
        entity.setCreatedAt(LocalDateTime.now());
        PhysiologicalResultEntity savedEntity = physiologicalResultRepository.save(entity);
        return getPhysiologicalResultResponse(savedEntity.getPhysiologicalExamResultId());
    }

    @Override
    @Transactional
    public PhysiologicalResultDTO modifyPhysiologicalResult(String id, PhysiologicalResultUpdateReqDTO dto) {
        PhysiologicalResultEntity entity = physiologicalResultRepository.findById(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Physiological result not found. id=" + id));
        entity.setResultValue(dto.getResultValue());
        entity.setReport(dto.getReport());
        entity.setMeasuredItemCode(dto.getMeasuredItemCode());
        entity.setConfirmedAt(dto.getConfirmedAt());
        entity.setStatus(normalizeStatus(dto.getStatus()));
        PhysiologicalResultEntity savedEntity = physiologicalResultRepository.save(entity);
        return getPhysiologicalResultResponse(savedEntity.getPhysiologicalExamResultId());
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
        return specimenTestResultRepository.findSpecimenResultResponseList();
    }

    @Override
    public SpecimenTestResultDTO findSpecimenResultDetail(String id) {
        return getSpecimenResultResponse(id);
    }

    @Override
    @Transactional
    public SpecimenTestResultDTO registerSpecimenResult(SpecimenTestResultCreateReqDTO dto) {
        SpecimenTestResultEntity entity = new SpecimenTestResultEntity();
        entity.setSpecimenExamResultId(hasText(dto.getSpecimenExamResultId()) ? dto.getSpecimenExamResultId() : createSpecimenResultId());
        entity.setSpecimenExamId(dto.getSpecimenExamId());
        entity.setResultItemCode(dto.getResultItemCode());
        entity.setResultValue(dto.getResultValue());
        entity.setUnit(dto.getUnit());
        entity.setReferenceRange(dto.getReferenceRange());
        entity.setJudgement(dto.getJudgement());
        entity.setConfirmedAt(dto.getConfirmedAt());
        entity.setStatus(normalizeStatus(dto.getStatus()));
        entity.setCreatedAt(LocalDateTime.now());
        SpecimenTestResultEntity savedEntity = specimenTestResultRepository.save(entity);
        return getSpecimenResultResponse(savedEntity.getSpecimenExamResultId());
    }

    @Override
    @Transactional
    public SpecimenTestResultDTO modifySpecimenResult(String id, SpecimenTestResultUpdateReqDTO dto) {
        SpecimenTestResultEntity entity = specimenTestResultRepository.findById(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Specimen result not found. id=" + id));
        entity.setResultItemCode(dto.getResultItemCode());
        entity.setResultValue(dto.getResultValue());
        entity.setUnit(dto.getUnit());
        entity.setReferenceRange(dto.getReferenceRange());
        entity.setJudgement(dto.getJudgement());
        entity.setConfirmedAt(dto.getConfirmedAt());
        entity.setStatus(normalizeStatus(dto.getStatus()));
        SpecimenTestResultEntity savedEntity = specimenTestResultRepository.save(entity);
        return getSpecimenResultResponse(savedEntity.getSpecimenExamResultId());
    }

    @Override
    @Transactional
    public void deleteSpecimenResult(String id) {
        SpecimenTestResultEntity entity = specimenTestResultRepository.findById(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Specimen result not found. id=" + id));
        entity.setStatus("INACTIVE");
        specimenTestResultRepository.save(entity);
    }

    private ImagingResultDTO getImagingResultResponse(String id) {
        return imagingResultRepository.findImagingResultResponseDetail(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Imaging result not found. id=" + id));
    }

    private EndoscopyResultDTO getEndoscopyResultResponse(String id) {
        return endoscopyResultRepository.findEndoscopyResultResponseDetail(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Endoscopy result not found. id=" + id));
    }

    private PathologyResultDTO getPathologyResultResponse(String id) {
        return pathologyResultRepository.findPathologyResultResponseDetail(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Pathology result not found. id=" + id));
    }

    private PhysiologicalResultDTO getPhysiologicalResultResponse(String id) {
        return physiologicalResultRepository.findPhysiologicalResultResponseDetail(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Physiological result not found. id=" + id));
    }

    private SpecimenTestResultDTO getSpecimenResultResponse(String id) {
        return specimenTestResultRepository.findSpecimenResultResponseDetail(id)
                .orElseThrow(() -> new DiagnosticResultNotFoundException("Specimen result not found. id=" + id));
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
