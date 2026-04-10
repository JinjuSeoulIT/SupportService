package com.app.medical_support.diagnosticexecution.service;

import com.app.medical_support.diagnosticexecution.dto.EndoscopyDTO;
import com.app.medical_support.diagnosticexecution.dto.ImagingDTO;
import com.app.medical_support.diagnosticexecution.dto.PathologyDTO;
import com.app.medical_support.diagnosticexecution.dto.PhysiologicalDTO;
import com.app.medical_support.diagnosticexecution.dto.SpecimenDTO;
import com.app.medical_support.diagnosticexecution.dto.TestExecutionDTO;
import com.app.medical_support.diagnosticexecution.dto.TestExecutionUpdateDTO;
import com.app.medical_support.diagnosticexecution.entity.EndoscopyEntity;
import com.app.medical_support.diagnosticexecution.entity.ImagingEntity;
import com.app.medical_support.diagnosticexecution.entity.PathologyEntity;
import com.app.medical_support.diagnosticexecution.entity.PhysiologicalEntity;
import com.app.medical_support.diagnosticexecution.entity.SpecimenEntity;
import com.app.medical_support.diagnosticexecution.entity.TestExecutionEntity;
import com.app.medical_support.diagnosticexecution.exception.DiagnosticExecutionNotFoundException;
import com.app.medical_support.diagnosticexecution.exception.SpecimenNotFoundException;
import com.app.medical_support.diagnosticexecution.exception.TestExecutionNotFoundExecution;
import com.app.medical_support.diagnosticexecution.mapstruct.SpecimenReqMapStruct;
import com.app.medical_support.diagnosticexecution.mapstruct.SpecimenResMapStruct;
import com.app.medical_support.diagnosticexecution.mapstruct.TestExecutionReqMapStruct;
import com.app.medical_support.diagnosticexecution.mapstruct.TestExecutionResMapStruct;
import com.app.medical_support.diagnosticexecution.repository.EndoscopyRepository;
import com.app.medical_support.diagnosticexecution.repository.ImagingRepository;
import com.app.medical_support.diagnosticexecution.repository.PathologyRepository;
import com.app.medical_support.diagnosticexecution.repository.PhysiologicalRepository;
import com.app.medical_support.diagnosticexecution.repository.SpecimenRepository;
import com.app.medical_support.diagnosticexecution.repository.TestExecutionRepository;
import com.app.medical_support.diagnosticresult.entity.EndoscopyResultEntity;
import com.app.medical_support.diagnosticresult.entity.ImagingResultEntity;
import com.app.medical_support.diagnosticresult.entity.PathologyResultEntity;
import com.app.medical_support.diagnosticresult.entity.PhysiologicalResultEntity;
import com.app.medical_support.diagnosticresult.entity.SpecimenTestResultEntity;
import com.app.medical_support.diagnosticresult.repository.EndoscopyResultRepository;
import com.app.medical_support.diagnosticresult.repository.ImagingResultRepository;
import com.app.medical_support.diagnosticresult.repository.PathologyResultRepository;
import com.app.medical_support.diagnosticresult.repository.PhysiologicalResultRepository;
import com.app.medical_support.diagnosticresult.repository.SpecimenTestResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DiagnosticExecutionServiceImpl implements DiagnosticExecutionService {

    private final ImagingRepository imagingRepository;
    private final ImagingResultRepository imagingResultRepository;
    private final EndoscopyRepository endoscopyRepository;
    private final EndoscopyResultRepository endoscopyResultRepository;
    private final PathologyRepository pathologyRepository;
    private final PathologyResultRepository pathologyResultRepository;
    private final PhysiologicalRepository physiologicalRepository;
    private final PhysiologicalResultRepository physiologicalResultRepository;
    private final SpecimenRepository specimenRepository;
    private final SpecimenTestResultRepository specimenTestResultRepository;
    private final SpecimenReqMapStruct specimenReqMapStruct;
    private final SpecimenResMapStruct specimenResMapStruct;
    private final TestExecutionRepository testExecutionRepository;
    private final TestExecutionReqMapStruct testExecutionReqMapStruct;
    private final TestExecutionResMapStruct testExecutionResMapStruct;

    @Override
    public List<ImagingDTO> findImagingList() {
        return imagingRepository.findAll().stream().map(this::toImagingDTO).toList();
    }

    @Override
    public ImagingDTO findImagingDetail(String id) {
        return toImagingDTO(imagingRepository.findById(id)
                .orElseThrow(() -> new DiagnosticExecutionNotFoundException("Imaging exam not found. id=" + id)));
    }

    @Override
    @Transactional
    public ImagingDTO registerImaging(ImagingDTO imagingDTO) {
        ImagingEntity entity = new ImagingEntity();
        entity.setImagingExamId(hasText(imagingDTO.getImagingExamId()) ? imagingDTO.getImagingExamId() : createImagingId());
        entity.setTestExecutionId(imagingDTO.getTestExecutionId());
        entity.setImagingType(imagingDTO.getImagingType());
        entity.setDetailCode(imagingDTO.getDetailCode());
        entity.setPatientId(imagingDTO.getPatientId());
        entity.setPatientName(imagingDTO.getPatientName());
        entity.setDepartmentName(imagingDTO.getDepartmentName());
        entity.setStatus(normalizeStatus(imagingDTO.getStatus()));
        entity.setProgressStatus(normalizeProgressStatus(imagingDTO.getProgressStatus()));
        entity.setPerformerId(normalizeOptionalValue(imagingDTO.getPerformerId()));
        entity.setPerformerName(normalizeOptionalValue(imagingDTO.getPerformerName()));
        entity.setCreatedAt(LocalDateTime.now());
        return toImagingDTO(imagingRepository.save(entity));
    }

    @Override
    @Transactional
    public ImagingDTO modifyImaging(String id, ImagingDTO imagingDTO) {
        ImagingEntity entity = imagingRepository.findById(id)
                .orElseThrow(() -> new DiagnosticExecutionNotFoundException("Imaging exam not found. id=" + id));
        String previousProgressStatus = entity.getProgressStatus();
        entity.setTestExecutionId(imagingDTO.getTestExecutionId());
        entity.setImagingType(imagingDTO.getImagingType());
        entity.setDetailCode(imagingDTO.getDetailCode());
        entity.setPatientId(imagingDTO.getPatientId());
        entity.setPatientName(imagingDTO.getPatientName());
        entity.setDepartmentName(imagingDTO.getDepartmentName());
        entity.setProgressStatus(resolveProgressStatus(imagingDTO.getProgressStatus(), entity.getProgressStatus()));
        entity.setPerformerId(normalizeOptionalValue(imagingDTO.getPerformerId()));
        entity.setPerformerName(normalizeOptionalValue(imagingDTO.getPerformerName()));
        entity.setUpdatedAt(LocalDateTime.now());
        ImagingEntity savedEntity = imagingRepository.save(entity);

        if (!isCompleted(previousProgressStatus) && isCompleted(savedEntity.getProgressStatus())) {
            ensureImagingResultExists(savedEntity);
        }

        return toImagingDTO(savedEntity);
    }

    @Override
    @Transactional
    public void deleteImaging(String id) {
        ImagingEntity entity = imagingRepository.findById(id)
                .orElseThrow(() -> new DiagnosticExecutionNotFoundException("Imaging exam not found. id=" + id));
        entity.setStatus("INACTIVE");
        entity.setUpdatedAt(LocalDateTime.now());
        imagingRepository.save(entity);
    }

    @Override
    public List<EndoscopyDTO> findEndoscopyList() {
        return endoscopyRepository.findAll().stream().map(this::toEndoscopyDTO).toList();
    }

    @Override
    public EndoscopyDTO findEndoscopyDetail(String id) {
        return toEndoscopyDTO(endoscopyRepository.findById(id)
                .orElseThrow(() -> new DiagnosticExecutionNotFoundException("Endoscopy exam not found. id=" + id)));
    }

    @Override
    @Transactional
    public EndoscopyDTO registerEndoscopy(EndoscopyDTO endoscopyDTO) {
        EndoscopyEntity entity = new EndoscopyEntity();
        entity.setEndoscopyExamId(hasText(endoscopyDTO.getEndoscopyExamId()) ? endoscopyDTO.getEndoscopyExamId() : createEndoscopyId());
        entity.setTestExecutionId(endoscopyDTO.getTestExecutionId());
        entity.setDetailCode(endoscopyDTO.getDetailCode());
        entity.setPatientId(endoscopyDTO.getPatientId());
        entity.setPatientName(endoscopyDTO.getPatientName());
        entity.setDepartmentName(endoscopyDTO.getDepartmentName());
        entity.setProcedureRoom(endoscopyDTO.getProcedureRoom());
        entity.setEquipment(endoscopyDTO.getEquipment());
        entity.setSedationYn(normalizeYnFlag(endoscopyDTO.getSedationYn()));
        entity.setPerformerId(normalizeOptionalValue(endoscopyDTO.getPerformerId()));
        entity.setPerformerName(normalizeOptionalValue(endoscopyDTO.getPerformerName()));
        entity.setProcedureAt(endoscopyDTO.getProcedureAt());
        entity.setStatus(normalizeStatus(endoscopyDTO.getStatus()));
        entity.setProgressStatus(normalizeProgressStatus(endoscopyDTO.getProgressStatus()));
        entity.setCreatedAt(LocalDateTime.now());
        return toEndoscopyDTO(endoscopyRepository.save(entity));
    }

    @Override
    @Transactional
    public EndoscopyDTO modifyEndoscopy(String id, EndoscopyDTO endoscopyDTO) {
        EndoscopyEntity entity = endoscopyRepository.findById(id)
                .orElseThrow(() -> new DiagnosticExecutionNotFoundException("Endoscopy exam not found. id=" + id));
        String previousProgressStatus = entity.getProgressStatus();
        entity.setTestExecutionId(endoscopyDTO.getTestExecutionId());
        entity.setDetailCode(endoscopyDTO.getDetailCode());
        entity.setPatientId(endoscopyDTO.getPatientId());
        entity.setPatientName(endoscopyDTO.getPatientName());
        entity.setDepartmentName(endoscopyDTO.getDepartmentName());
        entity.setProcedureRoom(endoscopyDTO.getProcedureRoom());
        entity.setEquipment(endoscopyDTO.getEquipment());
        entity.setSedationYn(normalizeYnFlag(endoscopyDTO.getSedationYn()));
        entity.setPerformerId(normalizeOptionalValue(endoscopyDTO.getPerformerId()));
        entity.setPerformerName(normalizeOptionalValue(endoscopyDTO.getPerformerName()));
        entity.setProcedureAt(endoscopyDTO.getProcedureAt());
        entity.setProgressStatus(resolveProgressStatus(endoscopyDTO.getProgressStatus(), entity.getProgressStatus()));
        entity.setUpdatedAt(LocalDateTime.now());
        EndoscopyEntity savedEntity = endoscopyRepository.save(entity);

        if (!isCompleted(previousProgressStatus) && isCompleted(savedEntity.getProgressStatus())) {
            ensureEndoscopyResultExists(savedEntity);
        }

        return toEndoscopyDTO(savedEntity);
    }

    @Override
    @Transactional
    public void deleteEndoscopy(String id) {
        EndoscopyEntity entity = endoscopyRepository.findById(id)
                .orElseThrow(() -> new DiagnosticExecutionNotFoundException("Endoscopy exam not found. id=" + id));
        entity.setStatus("INACTIVE");
        entity.setUpdatedAt(LocalDateTime.now());
        endoscopyRepository.save(entity);
    }

    @Override
    public List<PathologyDTO> findPathologyList() {
        return pathologyRepository.findAll().stream().map(this::toPathologyDTO).toList();
    }

    @Override
    public PathologyDTO findPathologyDetail(String id) {
        return toPathologyDTO(pathologyRepository.findById(id)
                .orElseThrow(() -> new DiagnosticExecutionNotFoundException("Pathology exam not found. id=" + id)));
    }

    @Override
    @Transactional
    public PathologyDTO registerPathology(PathologyDTO pathologyDTO) {
        PathologyEntity entity = new PathologyEntity();
        entity.setPathologyExamId(hasText(pathologyDTO.getPathologyExamId()) ? pathologyDTO.getPathologyExamId() : createPathologyId());
        entity.setTestExecutionId(pathologyDTO.getTestExecutionId());
        entity.setDetailCode(pathologyDTO.getDetailCode());
        entity.setPatientId(pathologyDTO.getPatientId());
        entity.setPatientName(pathologyDTO.getPatientName());
        entity.setDepartmentName(pathologyDTO.getDepartmentName());
        entity.setTissueStatus(pathologyDTO.getTissueStatus());
        entity.setCollectionMethod(pathologyDTO.getCollectionMethod());
        entity.setTissueSite(pathologyDTO.getTissueSite());
        entity.setTissueType(pathologyDTO.getTissueType());
        entity.setCollectedAt(pathologyDTO.getCollectedAt());
        entity.setPerformerId(normalizeOptionalValue(pathologyDTO.getPerformerId()));
        entity.setPerformerName(normalizeOptionalValue(pathologyDTO.getPerformerName()));
        entity.setReexamYn(normalizeYnFlag(pathologyDTO.getReexamYn()));
        entity.setStatus(normalizeStatus(pathologyDTO.getStatus()));
        entity.setProgressStatus(normalizeProgressStatus(pathologyDTO.getProgressStatus()));
        entity.setCreatedAt(LocalDateTime.now());
        return toPathologyDTO(pathologyRepository.save(entity));
    }

    @Override
    @Transactional
    public PathologyDTO modifyPathology(String id, PathologyDTO pathologyDTO) {
        PathologyEntity entity = pathologyRepository.findById(id)
                .orElseThrow(() -> new DiagnosticExecutionNotFoundException("Pathology exam not found. id=" + id));
        String previousProgressStatus = entity.getProgressStatus();
        entity.setTestExecutionId(pathologyDTO.getTestExecutionId());
        entity.setDetailCode(pathologyDTO.getDetailCode());
        entity.setPatientId(pathologyDTO.getPatientId());
        entity.setPatientName(pathologyDTO.getPatientName());
        entity.setDepartmentName(pathologyDTO.getDepartmentName());
        entity.setTissueStatus(pathologyDTO.getTissueStatus());
        entity.setCollectionMethod(pathologyDTO.getCollectionMethod());
        entity.setTissueSite(pathologyDTO.getTissueSite());
        entity.setTissueType(pathologyDTO.getTissueType());
        entity.setCollectedAt(pathologyDTO.getCollectedAt());
        entity.setPerformerId(normalizeOptionalValue(pathologyDTO.getPerformerId()));
        entity.setPerformerName(normalizeOptionalValue(pathologyDTO.getPerformerName()));
        entity.setReexamYn(normalizeYnFlag(pathologyDTO.getReexamYn()));
        entity.setProgressStatus(resolveProgressStatus(pathologyDTO.getProgressStatus(), entity.getProgressStatus()));
        entity.setUpdatedAt(LocalDateTime.now());
        PathologyEntity savedEntity = pathologyRepository.save(entity);

        if (!isCompleted(previousProgressStatus) && isCompleted(savedEntity.getProgressStatus())) {
            ensurePathologyResultExists(savedEntity);
        }

        return toPathologyDTO(savedEntity);
    }

    @Override
    @Transactional
    public void deletePathology(String id) {
        PathologyEntity entity = pathologyRepository.findById(id)
                .orElseThrow(() -> new DiagnosticExecutionNotFoundException("Pathology exam not found. id=" + id));
        entity.setStatus("INACTIVE");
        entity.setUpdatedAt(LocalDateTime.now());
        pathologyRepository.save(entity);
    }

    @Override
    public List<PhysiologicalDTO> findPhysiologicalList() {
        return physiologicalRepository.findAll().stream().map(this::toPhysiologicalDTO).toList();
    }

    @Override
    public PhysiologicalDTO findPhysiologicalDetail(String id) {
        return toPhysiologicalDTO(physiologicalRepository.findById(id)
                .orElseThrow(() -> new DiagnosticExecutionNotFoundException("Physiological exam not found. id=" + id)));
    }

    @Override
    @Transactional
    public PhysiologicalDTO registerPhysiological(PhysiologicalDTO physiologicalDTO) {
        PhysiologicalEntity entity = new PhysiologicalEntity();
        entity.setPhysiologicalExamId(hasText(physiologicalDTO.getPhysiologicalExamId()) ? physiologicalDTO.getPhysiologicalExamId() : createPhysiologicalId());
        entity.setTestExecutionId(physiologicalDTO.getTestExecutionId());
        entity.setDetailCode(physiologicalDTO.getDetailCode());
        entity.setPatientId(physiologicalDTO.getPatientId());
        entity.setPatientName(physiologicalDTO.getPatientName());
        entity.setDepartmentName(physiologicalDTO.getDepartmentName());
        entity.setExamEquipmentId(physiologicalDTO.getExamEquipmentId());
        entity.setRawData(physiologicalDTO.getRawData());
        entity.setReportDocId(physiologicalDTO.getReportDocId());
        entity.setPerformerId(normalizeOptionalValue(physiologicalDTO.getPerformerId()));
        entity.setPerformerName(normalizeOptionalValue(physiologicalDTO.getPerformerName()));
        entity.setStatus(normalizeStatus(physiologicalDTO.getStatus()));
        entity.setProgressStatus(normalizeProgressStatus(physiologicalDTO.getProgressStatus()));
        entity.setCreatedAt(LocalDateTime.now());
        return toPhysiologicalDTO(physiologicalRepository.save(entity));
    }

    @Override
    @Transactional
    public PhysiologicalDTO modifyPhysiological(String id, PhysiologicalDTO physiologicalDTO) {
        PhysiologicalEntity entity = physiologicalRepository.findById(id)
                .orElseThrow(() -> new DiagnosticExecutionNotFoundException("Physiological exam not found. id=" + id));
        String previousProgressStatus = entity.getProgressStatus();
        entity.setTestExecutionId(physiologicalDTO.getTestExecutionId());
        entity.setDetailCode(physiologicalDTO.getDetailCode());
        entity.setPatientId(physiologicalDTO.getPatientId());
        entity.setPatientName(physiologicalDTO.getPatientName());
        entity.setDepartmentName(physiologicalDTO.getDepartmentName());
        entity.setExamEquipmentId(physiologicalDTO.getExamEquipmentId());
        entity.setRawData(physiologicalDTO.getRawData());
        entity.setReportDocId(physiologicalDTO.getReportDocId());
        entity.setPerformerId(normalizeOptionalValue(physiologicalDTO.getPerformerId()));
        entity.setPerformerName(normalizeOptionalValue(physiologicalDTO.getPerformerName()));
        entity.setProgressStatus(resolveProgressStatus(physiologicalDTO.getProgressStatus(), entity.getProgressStatus()));
        entity.setUpdatedAt(LocalDateTime.now());
        PhysiologicalEntity savedEntity = physiologicalRepository.save(entity);

        if (!isCompleted(previousProgressStatus) && isCompleted(savedEntity.getProgressStatus())) {
            ensurePhysiologicalResultExists(savedEntity);
        }

        return toPhysiologicalDTO(savedEntity);
    }

    @Override
    @Transactional
    public void deletePhysiological(String id) {
        PhysiologicalEntity entity = physiologicalRepository.findById(id)
                .orElseThrow(() -> new DiagnosticExecutionNotFoundException("Physiological exam not found. id=" + id));
        entity.setStatus("INACTIVE");
        entity.setUpdatedAt(LocalDateTime.now());
        physiologicalRepository.save(entity);
    }

    @Override
    public List<SpecimenDTO> searchSpecimen(String searchType, String searchValue) {
        if (!hasText(searchType) || !hasText(searchValue)) {
            throw new SpecimenNotFoundException("Search type and search value are required.");
        }

        List<SpecimenEntity> result;
        String normalizedType = searchType.trim();

        if ("testExecutionId".equals(normalizedType)) {
            result = specimenRepository.findByTestExecutionId(searchValue);
        } else if ("specimenType".equals(normalizedType)) {
            result = specimenRepository.findBySpecimenType(searchValue);
        } else if ("specimenStatus".equals(normalizedType)) {
            result = specimenRepository.findBySpecimenStatus(searchValue);
        } else {
            throw new SpecimenNotFoundException("Unsupported search type. searchType=" + searchType);
        }

        return specimenResMapStruct.toDTOList(result);
    }

    @Override
    public List<SpecimenDTO> findSpecimenList() {
        return specimenResMapStruct.toDTOList(specimenRepository.findAll());
    }

    @Override
    public SpecimenDTO findSpecimenDetail(String id) {
        return specimenResMapStruct.toDTO(specimenRepository.findById(id)
                .orElseThrow(() -> new SpecimenNotFoundException("Specimen exam not found. id=" + id)));
    }

    @Override
    @Transactional
    public SpecimenDTO registerSpecimen(SpecimenDTO specimenDTO) {
        SpecimenEntity entity = specimenReqMapStruct.toEntity(specimenDTO);
        if (!hasText(entity.getSpecimenExamId())) {
            entity.setSpecimenExamId(createSpecimenId());
        }
        entity.setSpecimenStatus(hasText(entity.getSpecimenStatus()) ? entity.getSpecimenStatus().trim().toUpperCase() : "COLLECTED");
        entity.setRecollectionYn(normalizeYnFlag(entity.getRecollectionYn()));
        entity.setPerformerId(normalizeOptionalValue(entity.getPerformerId()));
        entity.setPerformerName(normalizeOptionalValue(entity.getPerformerName()));
        entity.setStatus(normalizeStatus(entity.getStatus()));
        entity.setProgressStatus(normalizeProgressStatus(entity.getProgressStatus()));
        entity.setCreatedAt(LocalDateTime.now());
        return specimenResMapStruct.toDTO(specimenRepository.save(entity));
    }

    @Override
    @Transactional
    public SpecimenDTO modifySpecimen(String id, SpecimenDTO specimenDTO) {
        SpecimenEntity entity = specimenRepository.findById(id)
                .orElseThrow(() -> new SpecimenNotFoundException("Specimen exam not found. id=" + id));
        String previousProgressStatus = entity.getProgressStatus();
        entity.setTestExecutionId(specimenDTO.getTestExecutionId());
        entity.setDetailCode(specimenDTO.getDetailCode());
        entity.setPatientId(specimenDTO.getPatientId());
        entity.setPatientName(specimenDTO.getPatientName());
        entity.setDepartmentName(specimenDTO.getDepartmentName());
        entity.setSpecimenType(specimenDTO.getSpecimenType());
        entity.setSpecimenStatus(hasText(specimenDTO.getSpecimenStatus()) ? specimenDTO.getSpecimenStatus().trim().toUpperCase() : entity.getSpecimenStatus());
        entity.setCollectedAt(specimenDTO.getCollectedAt());
        entity.setPerformerId(normalizeOptionalValue(specimenDTO.getPerformerId()));
        entity.setPerformerName(normalizeOptionalValue(specimenDTO.getPerformerName()));
        entity.setCollectionSite(specimenDTO.getCollectionSite());
        entity.setRecollectionYn(normalizeYnFlag(specimenDTO.getRecollectionYn()));
        entity.setProgressStatus(resolveProgressStatus(specimenDTO.getProgressStatus(), entity.getProgressStatus()));
        entity.setUpdatedAt(LocalDateTime.now());
        SpecimenEntity savedEntity = specimenRepository.save(entity);

        if (!isCompleted(previousProgressStatus) && isCompleted(savedEntity.getProgressStatus())) {
            ensureSpecimenResultExists(savedEntity);
        }

        return specimenResMapStruct.toDTO(savedEntity);
    }

    @Override
    @Transactional
    public void deleteSpecimen(String id) {
        SpecimenEntity entity = specimenRepository.findById(id)
                .orElseThrow(() -> new SpecimenNotFoundException("Specimen exam not found. id=" + id));
        entity.setStatus("INACTIVE");
        entity.setUpdatedAt(LocalDateTime.now());
        specimenRepository.save(entity);
    }

    @Override
    public List<TestExecutionDTO> findTestExecutionList(String executionType) {
        if (!hasText(executionType)) {
            return testExecutionResMapStruct.toDTOList(testExecutionRepository.findAll());
        }

        String normalized = executionType.trim().toUpperCase();
        return testExecutionResMapStruct.toDTOList(testExecutionRepository.findByExecutionTypeAndProgressStatus(normalized, "IN_PROGRESS"));
    }

    @Override
    public TestExecutionDTO findTestExecutionDetail(String id) {
        return testExecutionResMapStruct.toDTO(testExecutionRepository.findById(id)
                .orElseThrow(() -> new TestExecutionNotFoundExecution("Test execution not found. id=" + id)));
    }

    @Override
    @Transactional
    public TestExecutionDTO registerTestExecution(TestExecutionDTO testExecutionDTO) {
        TestExecutionEntity entity = testExecutionReqMapStruct.toEntity(testExecutionDTO);
        entity.setDetailCode(testExecutionDTO.getDetailCode());

        if (!hasText(entity.getTestExecutionId())) {
            entity.setTestExecutionId(createTestExecutionId());
        }
        entity.setStatus(normalizeStatus(testExecutionDTO.getStatus()));
        entity.setCreatedAt(LocalDateTime.now());
        if (!hasText(entity.getProgressStatus())) {
            entity.setProgressStatus("WAITING");
        }
        if (entity.getRetryNo() == null) {
            entity.setRetryNo(0);
        }
        return testExecutionResMapStruct.toDTO(testExecutionRepository.save(entity));
    }

    @Override
    @Transactional
    public TestExecutionDTO modifyTestExecution(String id, TestExecutionUpdateDTO testExecutionUpdateDTO) {
        TestExecutionEntity entity = testExecutionRepository.findById(id)
                .orElseThrow(() -> new TestExecutionNotFoundExecution("Test execution not found. id=" + id));


        String previousProgressStatus = entity.getProgressStatus();
        entity.setProgressStatus(testExecutionUpdateDTO.getProgressStatus());
        entity.setStatus(hasText(testExecutionUpdateDTO.getStatus())
                ? normalizeStatus(testExecutionUpdateDTO.getStatus())
                : normalizeStatus(entity.getStatus()));
        entity.setRetryNo(testExecutionUpdateDTO.getRetryNo());
        entity.setDetailCode(testExecutionUpdateDTO.getDetailCode());
        entity.setPerformerId(testExecutionUpdateDTO.getPerformerId());
        entity.setPatientId(testExecutionUpdateDTO.getPatientId());
        entity.setPatientName(testExecutionUpdateDTO.getPatientName());
        entity.setDepartmentName(testExecutionUpdateDTO.getDepartmentName());
        entity.setUpdatedAt(LocalDateTime.now());

        if (!isInProgress(previousProgressStatus) && isInProgress(entity.getProgressStatus())) {
            ensureExamRecordExists(entity);
        }

        return testExecutionResMapStruct.toDTO(testExecutionRepository.save(entity));
    }

    private ImagingDTO toImagingDTO(ImagingEntity entity) {
        ImagingDTO dto = new ImagingDTO();
        dto.setImagingExamId(entity.getImagingExamId());
        dto.setTestExecutionId(entity.getTestExecutionId());
        dto.setImagingType(entity.getImagingType());
        dto.setDetailCode(entity.getDetailCode());
        dto.setPatientId(entity.getPatientId());
        dto.setPatientName(entity.getPatientName());
        dto.setDepartmentName(entity.getDepartmentName());
        dto.setStatus(entity.getStatus());
        dto.setProgressStatus(entity.getProgressStatus());
        dto.setPerformerId(entity.getPerformerId());
        dto.setPerformerName(entity.getPerformerName());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private EndoscopyDTO toEndoscopyDTO(EndoscopyEntity entity) {
        EndoscopyDTO dto = new EndoscopyDTO();
        dto.setEndoscopyExamId(entity.getEndoscopyExamId());
        dto.setTestExecutionId(entity.getTestExecutionId());
        dto.setDetailCode(entity.getDetailCode());
        dto.setPatientId(entity.getPatientId());
        dto.setPatientName(entity.getPatientName());
        dto.setDepartmentName(entity.getDepartmentName());
        dto.setProcedureRoom(entity.getProcedureRoom());
        dto.setEquipment(entity.getEquipment());
        dto.setSedationYn(entity.getSedationYn());
        dto.setPerformerId(entity.getPerformerId());
        dto.setPerformerName(entity.getPerformerName());
        dto.setProcedureAt(entity.getProcedureAt());
        dto.setStatus(entity.getStatus());
        dto.setProgressStatus(entity.getProgressStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private PathologyDTO toPathologyDTO(PathologyEntity entity) {
        PathologyDTO dto = new PathologyDTO();
        dto.setPathologyExamId(entity.getPathologyExamId());
        dto.setTestExecutionId(entity.getTestExecutionId());
        dto.setDetailCode(entity.getDetailCode());
        dto.setPatientId(entity.getPatientId());
        dto.setPatientName(entity.getPatientName());
        dto.setDepartmentName(entity.getDepartmentName());
        dto.setTissueStatus(entity.getTissueStatus());
        dto.setCollectionMethod(entity.getCollectionMethod());
        dto.setTissueSite(entity.getTissueSite());
        dto.setTissueType(entity.getTissueType());
        dto.setCollectedAt(entity.getCollectedAt());
        dto.setPerformerId(entity.getPerformerId());
        dto.setPerformerName(entity.getPerformerName());
        dto.setReexamYn(entity.getReexamYn());
        dto.setStatus(entity.getStatus());
        dto.setProgressStatus(entity.getProgressStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private PhysiologicalDTO toPhysiologicalDTO(PhysiologicalEntity entity) {
        PhysiologicalDTO dto = new PhysiologicalDTO();
        dto.setPhysiologicalExamId(entity.getPhysiologicalExamId());
        dto.setTestExecutionId(entity.getTestExecutionId());
        dto.setDetailCode(entity.getDetailCode());
        dto.setPatientId(entity.getPatientId());
        dto.setPatientName(entity.getPatientName());
        dto.setDepartmentName(entity.getDepartmentName());
        dto.setExamEquipmentId(entity.getExamEquipmentId());
        dto.setRawData(entity.getRawData());
        dto.setReportDocId(entity.getReportDocId());
        dto.setPerformerId(entity.getPerformerId());
        dto.setPerformerName(entity.getPerformerName());
        dto.setStatus(entity.getStatus());
        dto.setProgressStatus(entity.getProgressStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private void ensureExamRecordExists(TestExecutionEntity entity) {
        String executionType = normalizeExecutionType(entity.getExecutionType());

        switch (executionType) {
            case "IMAGING" -> ensureImagingExists(entity);
            case "ENDOSCOPY" -> ensureEndoscopyExists(entity);
            case "PATHOLOGY" -> ensurePathologyExists(entity);
            case "PHYSIOLOGICAL" -> ensurePhysiologicalExists(entity);
            case "SPECIMEN" -> ensureSpecimenExists(entity);
            default -> log.warn("Unsupported execution type for automatic exam creation. testExecutionId={}, executionType={}",
                    entity.getTestExecutionId(), entity.getExecutionType());
        }
    }

    private void ensureImagingExists(TestExecutionEntity entity) {
        if (imagingRepository.existsByTestExecutionId(entity.getTestExecutionId())) {
            return;
        }

        ImagingEntity imagingEntity = new ImagingEntity();
        imagingEntity.setImagingExamId(createImagingId());
        imagingEntity.setTestExecutionId(entity.getTestExecutionId());
        imagingEntity.setImagingType(normalizeExecutionType(entity.getExecutionType()));
        imagingEntity.setDetailCode(entity.getDetailCode());
        imagingEntity.setPatientId(entity.getPatientId());
        imagingEntity.setPatientName(entity.getPatientName());
        imagingEntity.setDepartmentName(entity.getDepartmentName());
        imagingEntity.setStatus("ACTIVE");
        imagingEntity.setProgressStatus("WAITING");
        imagingEntity.setCreatedAt(LocalDateTime.now());
        imagingRepository.save(imagingEntity);
    }

    private void ensureImagingResultExists(ImagingEntity entity) {
        if (imagingResultRepository.existsByImagingExamId(entity.getImagingExamId())) {
            return;
        }

        ImagingResultEntity resultEntity = new ImagingResultEntity();
        resultEntity.setImagingResultId(createImagingResultId());
        resultEntity.setImagingExamId(entity.getImagingExamId());
        resultEntity.setStatus("ACTIVE");
        resultEntity.setCreatedAt(LocalDateTime.now());
        imagingResultRepository.save(resultEntity);
    }

    private void ensureEndoscopyResultExists(EndoscopyEntity entity) {
        if (endoscopyResultRepository.existsByEndoscopyExamId(entity.getEndoscopyExamId())) {
            return;
        }

        EndoscopyResultEntity resultEntity = new EndoscopyResultEntity();
        resultEntity.setEndoscopyResultId(createEndoscopyResultId());
        resultEntity.setEndoscopyExamId(entity.getEndoscopyExamId());
        resultEntity.setStatus("ACTIVE");
        resultEntity.setCreatedAt(LocalDateTime.now());
        endoscopyResultRepository.save(resultEntity);
    }

    private void ensureEndoscopyExists(TestExecutionEntity entity) {
        if (endoscopyRepository.existsByTestExecutionId(entity.getTestExecutionId())) {
            return;
        }

        EndoscopyEntity endoscopyEntity = new EndoscopyEntity();
        endoscopyEntity.setEndoscopyExamId(createEndoscopyId());
        endoscopyEntity.setTestExecutionId(entity.getTestExecutionId());
        endoscopyEntity.setDetailCode(entity.getDetailCode());
        endoscopyEntity.setPatientId(entity.getPatientId());
        endoscopyEntity.setPatientName(entity.getPatientName());
        endoscopyEntity.setDepartmentName(entity.getDepartmentName());
        endoscopyEntity.setSedationYn("N");
        endoscopyEntity.setStatus("ACTIVE");
        endoscopyEntity.setProgressStatus("WAITING");
        endoscopyEntity.setCreatedAt(LocalDateTime.now());
        endoscopyRepository.save(endoscopyEntity);
    }

    private void ensurePathologyExists(TestExecutionEntity entity) {
        if (pathologyRepository.existsByTestExecutionId(entity.getTestExecutionId())) {
            return;
        }

        PathologyEntity pathologyEntity = new PathologyEntity();
        pathologyEntity.setPathologyExamId(createPathologyId());
        pathologyEntity.setTestExecutionId(entity.getTestExecutionId());
        pathologyEntity.setDetailCode(entity.getDetailCode());
        pathologyEntity.setPatientId(entity.getPatientId());
        pathologyEntity.setPatientName(entity.getPatientName());
        pathologyEntity.setDepartmentName(entity.getDepartmentName());
        pathologyEntity.setReexamYn("N");
        pathologyEntity.setStatus("ACTIVE");
        pathologyEntity.setProgressStatus("WAITING");
        pathologyEntity.setCreatedAt(LocalDateTime.now());
        pathologyRepository.save(pathologyEntity);
    }

    private void ensurePathologyResultExists(PathologyEntity entity) {
        if (pathologyResultRepository.existsByPathologyExamId(entity.getPathologyExamId())) {
            return;
        }

        PathologyResultEntity resultEntity = new PathologyResultEntity();
        resultEntity.setPathologyExamResultId(createPathologyResultId());
        resultEntity.setPathologyExamId(entity.getPathologyExamId());
        resultEntity.setStatus("ACTIVE");
        resultEntity.setCreatedAt(LocalDateTime.now());
        pathologyResultRepository.save(resultEntity);
    }

    private void ensurePhysiologicalExists(TestExecutionEntity entity) {
        if (physiologicalRepository.existsByTestExecutionId(entity.getTestExecutionId())) {
            return;
        }

        PhysiologicalEntity physiologicalEntity = new PhysiologicalEntity();
        physiologicalEntity.setPhysiologicalExamId(createPhysiologicalId());
        physiologicalEntity.setTestExecutionId(entity.getTestExecutionId());
        physiologicalEntity.setDetailCode(entity.getDetailCode());
        physiologicalEntity.setPatientId(entity.getPatientId());
        physiologicalEntity.setPatientName(entity.getPatientName());
        physiologicalEntity.setDepartmentName(entity.getDepartmentName());
        physiologicalEntity.setStatus("ACTIVE");
        physiologicalEntity.setProgressStatus("WAITING");
        physiologicalEntity.setCreatedAt(LocalDateTime.now());
        physiologicalRepository.save(physiologicalEntity);
    }

    private void ensurePhysiologicalResultExists(PhysiologicalEntity entity) {
        if (physiologicalResultRepository.existsByPhysiologicalExamId(entity.getPhysiologicalExamId())) {
            return;
        }

        PhysiologicalResultEntity resultEntity = new PhysiologicalResultEntity();
        resultEntity.setPhysiologicalExamResultId(createPhysiologicalResultId());
        resultEntity.setPhysiologicalExamId(entity.getPhysiologicalExamId());
        resultEntity.setStatus("ACTIVE");
        resultEntity.setCreatedAt(LocalDateTime.now());
        physiologicalResultRepository.save(resultEntity);
    }

    private void ensureSpecimenExists(TestExecutionEntity entity) {
        if (specimenRepository.existsByTestExecutionId(entity.getTestExecutionId())) {
            return;
        }

        SpecimenEntity specimenEntity = new SpecimenEntity();
        specimenEntity.setSpecimenExamId(createSpecimenId());
        specimenEntity.setTestExecutionId(entity.getTestExecutionId());
        specimenEntity.setSpecimenType(normalizeExecutionType(entity.getExecutionType()));
        specimenEntity.setDetailCode(entity.getDetailCode());
        specimenEntity.setPatientId(entity.getPatientId());
        specimenEntity.setPatientName(entity.getPatientName());
        specimenEntity.setDepartmentName(entity.getDepartmentName());
        specimenEntity.setSpecimenStatus("COLLECTED");
        specimenEntity.setRecollectionYn("N");
        specimenEntity.setStatus("ACTIVE");
        specimenEntity.setProgressStatus("WAITING");
        specimenEntity.setCreatedAt(LocalDateTime.now());
        specimenRepository.save(specimenEntity);
    }

    private void ensureSpecimenResultExists(SpecimenEntity entity) {
        if (specimenTestResultRepository.existsBySpecimenExamId(entity.getSpecimenExamId())) {
            return;
        }

        SpecimenTestResultEntity resultEntity = new SpecimenTestResultEntity();
        resultEntity.setSpecimenExamResultId(createSpecimenResultId());
        resultEntity.setSpecimenExamId(entity.getSpecimenExamId());
        resultEntity.setStatus("ACTIVE");
        resultEntity.setCreatedAt(LocalDateTime.now());
        specimenTestResultRepository.save(resultEntity);
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private boolean isInProgress(String progressStatus) {
        return "IN_PROGRESS".equalsIgnoreCase(progressStatus != null ? progressStatus.trim() : null);
    }

    private boolean isCompleted(String progressStatus) {
        return "COMPLETED".equalsIgnoreCase(progressStatus != null ? progressStatus.trim() : null);
    }

    private String normalizeExecutionType(String executionType) {
        return hasText(executionType) ? executionType.trim().toUpperCase() : "";
    }

    private String createSpecimenId() {
        return "SPEC_EXAM_" + System.currentTimeMillis();
    }

    private String createTestExecutionId() {
        return "TEST_EXECUTION_" + System.currentTimeMillis();
    }

    private String createImagingId() {
        return "IMG_" + System.currentTimeMillis();
    }

    private String createEndoscopyId() {
        return "ENDO_" + System.currentTimeMillis();
    }

    private String createImagingResultId() {
        return "IMG_RES_" + System.currentTimeMillis();
    }

    private String createEndoscopyResultId() {
        return "ENDO_RES_" + System.currentTimeMillis();
    }

    private String createPathologyId() {
        return "PATH_" + System.currentTimeMillis();
    }

    private String createPathologyResultId() {
        return "PATH_RES_" + System.currentTimeMillis();
    }

    private String createPhysiologicalId() {
        return "PHYS_" + System.currentTimeMillis();
    }

    private String createPhysiologicalResultId() {
        return "PHYS_RES_" + System.currentTimeMillis();
    }

    private String createSpecimenResultId() {
        return "SPEC_RES_" + System.currentTimeMillis();
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

    private String normalizeProgressStatus(String value) {
        if (!hasText(value)) {
            return "WAITING";
        }

        String trimmed = value.trim().toUpperCase();
        if ("WAITING".equals(trimmed) || "IN_PROGRESS".equals(trimmed) || "COMPLETED".equals(trimmed)) {
            return trimmed;
        }

        return "WAITING";
    }

    private String resolveProgressStatus(String newValue, String currentValue) {
        if (!hasText(newValue)) {
            return hasText(currentValue) ? normalizeProgressStatus(currentValue) : "WAITING";
        }

        return normalizeProgressStatus(newValue);
    }

    private String normalizeOptionalValue(String value) {
        return hasText(value) ? value.trim() : null;
    }
}
