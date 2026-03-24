package com.app.medical_support.diagnosticexecution.service;

import com.app.medical_support.diagnosticexecution.dto.EndoscopyDTO;
import com.app.medical_support.diagnosticexecution.dto.ImagingDTO;
import com.app.medical_support.diagnosticexecution.dto.PathologyDTO;
import com.app.medical_support.diagnosticexecution.dto.PhysiologicalDTO;
import com.app.medical_support.diagnosticexecution.dto.SpecimenDTO;
import com.app.medical_support.diagnosticexecution.dto.TestExecutionDTO;
import com.app.medical_support.diagnosticexecution.dto.TestExecutionReqDTO;
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
    private final EndoscopyRepository endoscopyRepository;
    private final PathologyRepository pathologyRepository;
    private final PhysiologicalRepository physiologicalRepository;
    private final SpecimenRepository specimenRepository;
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
        entity.setVisitId(imagingDTO.getVisitId());
        entity.setImagingType(imagingDTO.getImagingType());
        entity.setExamStatusYn(normalizeYnStatus(imagingDTO.getExamStatusYn()));
        entity.setExamAt(imagingDTO.getExamAt());
        entity.setCreatedAt(LocalDateTime.now());
        return toImagingDTO(imagingRepository.save(entity));
    }

    @Override
    @Transactional
    public ImagingDTO modifyImaging(String id, ImagingDTO imagingDTO) {
        ImagingEntity entity = imagingRepository.findById(id)
                .orElseThrow(() -> new DiagnosticExecutionNotFoundException("Imaging exam not found. id=" + id));
        entity.setVisitId(imagingDTO.getVisitId());
        entity.setImagingType(imagingDTO.getImagingType());
        entity.setExamStatusYn(normalizeYnStatus(imagingDTO.getExamStatusYn()));
        entity.setExamAt(imagingDTO.getExamAt());
        entity.setUpdatedAt(LocalDateTime.now());
        return toImagingDTO(imagingRepository.save(entity));
    }

    @Override
    @Transactional
    public void deleteImaging(String id) {
        ImagingEntity entity = imagingRepository.findById(id)
                .orElseThrow(() -> new DiagnosticExecutionNotFoundException("Imaging exam not found. id=" + id));
        entity.setExamStatusYn("N");
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
        entity.setProcedureRoom(endoscopyDTO.getProcedureRoom());
        entity.setEquipment(endoscopyDTO.getEquipment());
        entity.setSedationYn(normalizeYnFlag(endoscopyDTO.getSedationYn()));
        entity.setOperationId(endoscopyDTO.getOperationId());
        entity.setProcedureAt(endoscopyDTO.getProcedureAt());
        entity.setStatus(normalizeStatus(endoscopyDTO.getStatus()));
        entity.setCreatedAt(LocalDateTime.now());
        return toEndoscopyDTO(endoscopyRepository.save(entity));
    }

    @Override
    @Transactional
    public EndoscopyDTO modifyEndoscopy(String id, EndoscopyDTO endoscopyDTO) {
        EndoscopyEntity entity = endoscopyRepository.findById(id)
                .orElseThrow(() -> new DiagnosticExecutionNotFoundException("Endoscopy exam not found. id=" + id));
        entity.setTestExecutionId(endoscopyDTO.getTestExecutionId());
        entity.setProcedureRoom(endoscopyDTO.getProcedureRoom());
        entity.setEquipment(endoscopyDTO.getEquipment());
        entity.setSedationYn(normalizeYnFlag(endoscopyDTO.getSedationYn()));
        entity.setOperationId(endoscopyDTO.getOperationId());
        entity.setProcedureAt(endoscopyDTO.getProcedureAt());
        entity.setStatus(normalizeStatus(endoscopyDTO.getStatus()));
        entity.setUpdatedAt(LocalDateTime.now());
        return toEndoscopyDTO(endoscopyRepository.save(entity));
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
        entity.setTissueStatus(pathologyDTO.getTissueStatus());
        entity.setCollectionMethod(pathologyDTO.getCollectionMethod());
        entity.setTissueSite(pathologyDTO.getTissueSite());
        entity.setTissueType(pathologyDTO.getTissueType());
        entity.setCollectedAt(pathologyDTO.getCollectedAt());
        entity.setCollectedById(pathologyDTO.getCollectedById());
        entity.setReexamYn(normalizeYnFlag(pathologyDTO.getReexamYn()));
        entity.setStatus(normalizeStatus(pathologyDTO.getStatus()));
        entity.setCreatedAt(LocalDateTime.now());
        return toPathologyDTO(pathologyRepository.save(entity));
    }

    @Override
    @Transactional
    public PathologyDTO modifyPathology(String id, PathologyDTO pathologyDTO) {
        PathologyEntity entity = pathologyRepository.findById(id)
                .orElseThrow(() -> new DiagnosticExecutionNotFoundException("Pathology exam not found. id=" + id));
        entity.setTestExecutionId(pathologyDTO.getTestExecutionId());
        entity.setTissueStatus(pathologyDTO.getTissueStatus());
        entity.setCollectionMethod(pathologyDTO.getCollectionMethod());
        entity.setTissueSite(pathologyDTO.getTissueSite());
        entity.setTissueType(pathologyDTO.getTissueType());
        entity.setCollectedAt(pathologyDTO.getCollectedAt());
        entity.setCollectedById(pathologyDTO.getCollectedById());
        entity.setReexamYn(normalizeYnFlag(pathologyDTO.getReexamYn()));
        entity.setStatus(normalizeStatus(pathologyDTO.getStatus()));
        entity.setUpdatedAt(LocalDateTime.now());
        return toPathologyDTO(pathologyRepository.save(entity));
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
        entity.setExamEquipmentId(physiologicalDTO.getExamEquipmentId());
        entity.setRawData(physiologicalDTO.getRawData());
        entity.setReportDocId(physiologicalDTO.getReportDocId());
        entity.setStatus(normalizeStatus(physiologicalDTO.getStatus()));
        entity.setCreatedAt(LocalDateTime.now());
        return toPhysiologicalDTO(physiologicalRepository.save(entity));
    }

    @Override
    @Transactional
    public PhysiologicalDTO modifyPhysiological(String id, PhysiologicalDTO physiologicalDTO) {
        PhysiologicalEntity entity = physiologicalRepository.findById(id)
                .orElseThrow(() -> new DiagnosticExecutionNotFoundException("Physiological exam not found. id=" + id));
        entity.setTestExecutionId(physiologicalDTO.getTestExecutionId());
        entity.setExamEquipmentId(physiologicalDTO.getExamEquipmentId());
        entity.setRawData(physiologicalDTO.getRawData());
        entity.setReportDocId(physiologicalDTO.getReportDocId());
        entity.setStatus(normalizeStatus(physiologicalDTO.getStatus()));
        entity.setUpdatedAt(LocalDateTime.now());
        return toPhysiologicalDTO(physiologicalRepository.save(entity));
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
        entity.setStatus(normalizeStatus(entity.getStatus()));
        entity.setCreatedAt(LocalDateTime.now());
        return specimenResMapStruct.toDTO(specimenRepository.save(entity));
    }

    @Override
    @Transactional
    public SpecimenDTO modifySpecimen(String id, SpecimenDTO specimenDTO) {
        SpecimenEntity entity = specimenRepository.findById(id)
                .orElseThrow(() -> new SpecimenNotFoundException("Specimen exam not found. id=" + id));
        entity.setTestExecutionId(specimenDTO.getTestExecutionId());
        entity.setSpecimenType(specimenDTO.getSpecimenType());
        entity.setSpecimenStatus(hasText(specimenDTO.getSpecimenStatus()) ? specimenDTO.getSpecimenStatus().trim().toUpperCase() : entity.getSpecimenStatus());
        entity.setCollectedAt(specimenDTO.getCollectedAt());
        entity.setCollectedById(specimenDTO.getCollectedById());
        entity.setCollectionSite(specimenDTO.getCollectionSite());
        entity.setRecollectionYn(normalizeYnFlag(specimenDTO.getRecollectionYn()));
        entity.setStatus(normalizeStatus(specimenDTO.getStatus()));
        entity.setUpdatedAt(LocalDateTime.now());
        return specimenResMapStruct.toDTO(specimenRepository.save(entity));
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
    public List<TestExecutionDTO> findTestExecutionList() {
        return testExecutionResMapStruct.toDTOList(testExecutionRepository.findAll());
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
        if (!hasText(entity.getTestExecutionId())) {
            entity.setTestExecutionId(createTestExecutionId());
        }
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
    public TestExecutionDTO modifyTestExecution(String id, TestExecutionReqDTO testExecutionReqDTO) {
        TestExecutionEntity entity = testExecutionRepository.findById(id)
                .orElseThrow(() -> new TestExecutionNotFoundExecution("Test execution not found. id=" + id));
        entity.setProgressStatus(testExecutionReqDTO.getProgressStatus());
        entity.setRetryNo(testExecutionReqDTO.getRetryNo());
        entity.setStartedAt(testExecutionReqDTO.getStartedAt());
        entity.setCompletedAt(testExecutionReqDTO.getCompletedAt());
        entity.setPerformerId(testExecutionReqDTO.getPerformerId());
        entity.setUpdatedAt(LocalDateTime.now());
        return testExecutionResMapStruct.toDTO(testExecutionRepository.save(entity));
    }

    private ImagingDTO toImagingDTO(ImagingEntity entity) {
        ImagingDTO dto = new ImagingDTO();
        dto.setImagingExamId(entity.getImagingExamId());
        dto.setVisitId(entity.getVisitId());
        dto.setImagingType(entity.getImagingType());
        dto.setExamStatusYn(entity.getExamStatusYn());
        dto.setExamAt(entity.getExamAt());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private EndoscopyDTO toEndoscopyDTO(EndoscopyEntity entity) {
        EndoscopyDTO dto = new EndoscopyDTO();
        dto.setEndoscopyExamId(entity.getEndoscopyExamId());
        dto.setTestExecutionId(entity.getTestExecutionId());
        dto.setProcedureRoom(entity.getProcedureRoom());
        dto.setEquipment(entity.getEquipment());
        dto.setSedationYn(entity.getSedationYn());
        dto.setOperationId(entity.getOperationId());
        dto.setProcedureAt(entity.getProcedureAt());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private PathologyDTO toPathologyDTO(PathologyEntity entity) {
        PathologyDTO dto = new PathologyDTO();
        dto.setPathologyExamId(entity.getPathologyExamId());
        dto.setTestExecutionId(entity.getTestExecutionId());
        dto.setTissueStatus(entity.getTissueStatus());
        dto.setCollectionMethod(entity.getCollectionMethod());
        dto.setTissueSite(entity.getTissueSite());
        dto.setTissueType(entity.getTissueType());
        dto.setCollectedAt(entity.getCollectedAt());
        dto.setCollectedById(entity.getCollectedById());
        dto.setReexamYn(entity.getReexamYn());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private PhysiologicalDTO toPhysiologicalDTO(PhysiologicalEntity entity) {
        PhysiologicalDTO dto = new PhysiologicalDTO();
        dto.setPhysiologicalExamId(entity.getPhysiologicalExamId());
        dto.setTestExecutionId(entity.getTestExecutionId());
        dto.setExamEquipmentId(entity.getExamEquipmentId());
        dto.setRawData(entity.getRawData());
        dto.setReportDocId(entity.getReportDocId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
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

    private String createPathologyId() {
        return "PATH_" + System.currentTimeMillis();
    }

    private String createPhysiologicalId() {
        return "PHYS_" + System.currentTimeMillis();
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

    private String normalizeYnStatus(String value) {
        if (!hasText(value)) {
            return "Y";
        }

        String trimmed = value.trim().toUpperCase();
        if ("ACTIVE".equals(trimmed) || "Y".equals(trimmed)) {
            return "Y";
        }

        return "N";
    }
}
