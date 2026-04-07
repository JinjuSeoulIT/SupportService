package com.app.medical_support.nursingtreatment.service;

import com.app.medical_support.common.integration.reception.dto.OutpatientReceptionDTO;
import com.app.medical_support.common.integration.reception.service.ReceptionIntegrationService;
import com.app.medical_support.nursingtreatment.dto.MedicationRecordDTO;
import com.app.medical_support.nursingtreatment.dto.RecordDTO;
import com.app.medical_support.nursingtreatment.dto.RecordRequestDTO;
import com.app.medical_support.nursingtreatment.dto.RecordResponseDTO;
import com.app.medical_support.nursingtreatment.dto.TreatmentResultDTO;
import com.app.medical_support.nursingtreatment.entity.MedicationRecordEntity;
import com.app.medical_support.nursingtreatment.entity.RecordEntity;
import com.app.medical_support.nursingtreatment.entity.TreatmentResultEntity;
import com.app.medical_support.nursingtreatment.exception.*;
import com.app.medical_support.nursingtreatment.mapper.RecordMapper;
import com.app.medical_support.nursingtreatment.mapstruct.RecordReqMapStruct;
import com.app.medical_support.nursingtreatment.mapstruct.RecordResMapStruct;
import com.app.medical_support.nursingtreatment.repository.MedicationRecordRepository;
import com.app.medical_support.nursingtreatment.repository.RecordRepository;
import com.app.medical_support.nursingtreatment.repository.TreatmentResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NursingTreatmentServiceImpl implements NursingTreatmentService {
    private final RecordRepository recordRepository;
    private final RecordReqMapStruct recordReqMapStruct;
    private final RecordResMapStruct recordResMapStruct;
    private final RecordMapper recordMapper;
    private final MedicationRecordRepository medicationRecordRepository;
    private final TreatmentResultRepository treatmentResultRepository;
    private final ReceptionIntegrationService receptionIntegrationService;

    @Override
    public List<RecordResponseDTO> search(String searchType, String searchValue, String startDate, String endDate) {
        if (!"recordId".equals(searchType)
                && !"nurseName".equals(searchType)
                && !"patientName".equals(searchType)
                && !"departmentName".equals(searchType)
                && !"createdAt".equals(searchType)) {
            throw new RecordSearchValidationException("지원하지 않는 검색 타입입니다: " + searchType);
        }
        return recordMapper.search(searchType, searchValue, startDate, endDate);


    }

    @Override
    public List<RecordResponseDTO> findRecordList() {
        return recordMapper.findRecordList();
    }

    @Override
    public RecordResponseDTO findRecordDetail(String id) {
        RecordResponseDTO recordResponseDTO = recordMapper.findRecordDetail(id);
        if (recordResponseDTO == null) {
            throw new RecordNotFoundException(id);
        }
        return recordResponseDTO;
    }

    @Override
    @Transactional
    public RecordDTO registerRecord(RecordRequestDTO recordRequestDTO) {
        validateReceptionRecordRequest(recordRequestDTO);

        RecordEntity entity = recordReqMapStruct.toEntity(recordRequestDTO);
        LocalDateTime now = LocalDateTime.now();

        if (!hasText(entity.getRecordId())) {
            entity.setRecordId(createRecordId());
        }

        entity.setStatus("ACTIVE");
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        return recordResMapStruct.toDTO(recordRepository.save(entity));
    }

    @Override
    @Transactional
    public RecordDTO modifyRecord(String id, RecordDTO recordDTO) {
        RecordEntity saved = recordRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));


        saved.setSystolicBp(recordDTO.getSystolicBp());
        saved.setDiastolicBp(recordDTO.getDiastolicBp());
        saved.setPulse(recordDTO.getPulse());
        saved.setRespiration(recordDTO.getRespiration());
        saved.setTemperature(recordDTO.getTemperature());
        saved.setSpo2(recordDTO.getSpo2());
        saved.setObservation(recordDTO.getObservation());
        saved.setPainScore(recordDTO.getPainScore());
        saved.setConsciousnessLevel(recordDTO.getConsciousnessLevel());
        saved.setInitialAssessment(recordDTO.getInitialAssessment());
        saved.setPastMedicalHistory(recordDTO.getPastMedicalHistory());
        saved.setStatus(normalizeStatus(recordDTO.getStatus()));
        saved.setReceptionId(recordDTO.getReceptionId());
        saved.setNursingId(recordDTO.getNursingId());
        saved.setHeightCm(recordDTO.getHeightCm());
        saved.setWeightKg(recordDTO.getWeightKg());
        saved.setUpdatedAt(LocalDateTime.now());

        return recordResMapStruct.toDTO(recordRepository.save(saved));
    }

    @Override
    @Transactional
    public RecordDTO updateRecordStatus(String id, String status) {
        RecordEntity entity = recordRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));

        entity.setStatus(normalizeStatus(status));
        entity.setUpdatedAt(LocalDateTime.now());
        return recordResMapStruct.toDTO(recordRepository.save(entity));
    }

    @Override
    public List<MedicationRecordDTO> findMedicationRecordList() {
        return medicationRecordRepository.findAll().stream().map(this::toMedicationRecordDTO).toList();
    }

    @Override
    public MedicationRecordDTO findMedicationRecordDetail(String id) {
        return toMedicationRecordDTO(medicationRecordRepository.findById(id)
                .orElseThrow(() -> new MedicationRecordNotFoundException(id)));
    }

    @Override
    @Transactional
    public MedicationRecordDTO registerMedicationRecord(MedicationRecordDTO medicationRecordDTO) {
        MedicationRecordEntity entity = new MedicationRecordEntity();
        entity.setMedicationId(hasText(medicationRecordDTO.getMedicationId()) ? medicationRecordDTO.getMedicationId() : createMedicationId());
        entity.setAdministeredAt(medicationRecordDTO.getAdministeredAt());
        entity.setDoseNumber(medicationRecordDTO.getDoseNumber());
        entity.setDoseUnit(medicationRecordDTO.getDoseUnit());
        entity.setNursingId(medicationRecordDTO.getNursingId());
        entity.setStatus(normalizeStatus(medicationRecordDTO.getStatus()));
        return toMedicationRecordDTO(medicationRecordRepository.save(entity));
    }

    @Override
    @Transactional
    public MedicationRecordDTO modifyMedicationRecord(String id, MedicationRecordDTO medicationRecordDTO) {
        MedicationRecordEntity entity = medicationRecordRepository.findById(id)
                .orElseThrow(() -> new MedicationRecordNotFoundException(id));
        entity.setAdministeredAt(medicationRecordDTO.getAdministeredAt());
        entity.setDoseNumber(medicationRecordDTO.getDoseNumber());
        entity.setDoseUnit(medicationRecordDTO.getDoseUnit());
        entity.setNursingId(medicationRecordDTO.getNursingId());
        entity.setStatus(normalizeStatus(medicationRecordDTO.getStatus()));
        return toMedicationRecordDTO(medicationRecordRepository.save(entity));
    }

    @Override
    @Transactional
    public MedicationRecordDTO updateMedicationRecordStatus(String id, String status) {
        MedicationRecordEntity entity = medicationRecordRepository.findById(id)
                .orElseThrow(() -> new MedicationRecordNotFoundException(id));
        entity.setStatus(normalizeStatus(status));
        return toMedicationRecordDTO(medicationRecordRepository.save(entity));
    }

    @Override
    public List<TreatmentResultDTO> findTreatmentResultList() {
        return treatmentResultRepository.findAll().stream().map(this::toTreatmentResultDTO).toList();
    }

    @Override
    public TreatmentResultDTO findTreatmentResultDetail(String id) {
        return toTreatmentResultDTO(treatmentResultRepository.findById(id)
                .orElseThrow(() -> new TreatmentResultNotFoundException(id)));
    }

    @Override
    @Transactional
    public TreatmentResultDTO registerTreatmentResult(TreatmentResultDTO treatmentResultDTO) {
        TreatmentResultEntity entity = new TreatmentResultEntity();
        entity.setProcedureResultId(hasText(treatmentResultDTO.getProcedureResultId()) ? treatmentResultDTO.getProcedureResultId() : createTreatmentResultId());
        entity.setPerformedAt(treatmentResultDTO.getPerformedAt());
        entity.setPerformerId(treatmentResultDTO.getPerformerId());
        entity.setDetail(treatmentResultDTO.getDetail());
        entity.setStatus(normalizeStatus(treatmentResultDTO.getStatus()));
        return toTreatmentResultDTO(treatmentResultRepository.save(entity));
    }

    @Override
    @Transactional
    public TreatmentResultDTO modifyTreatmentResult(String id, TreatmentResultDTO treatmentResultDTO) {
        TreatmentResultEntity entity = treatmentResultRepository.findById(id)
                .orElseThrow(() -> new TreatmentResultNotFoundException(id));
        entity.setPerformedAt(treatmentResultDTO.getPerformedAt());
        entity.setPerformerId(treatmentResultDTO.getPerformerId());
        entity.setDetail(treatmentResultDTO.getDetail());
        entity.setStatus(normalizeStatus(treatmentResultDTO.getStatus()));
        return toTreatmentResultDTO(treatmentResultRepository.save(entity));
    }

    @Override
    @Transactional
    public TreatmentResultDTO updateTreatmentResultStatus(String id, String status) {
        TreatmentResultEntity entity = treatmentResultRepository.findById(id)
                .orElseThrow(() -> new TreatmentResultNotFoundException(id));
        entity.setStatus(normalizeStatus(status));
        return toTreatmentResultDTO(treatmentResultRepository.save(entity));
    }

    private MedicationRecordDTO toMedicationRecordDTO(MedicationRecordEntity entity) {
        MedicationRecordDTO dto = new MedicationRecordDTO();
        dto.setMedicationId(entity.getMedicationId());
        dto.setAdministeredAt(entity.getAdministeredAt());
        dto.setDoseNumber(entity.getDoseNumber());
        dto.setDoseUnit(entity.getDoseUnit());
        dto.setNursingId(entity.getNursingId());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    private TreatmentResultDTO toTreatmentResultDTO(TreatmentResultEntity entity) {
        TreatmentResultDTO dto = new TreatmentResultDTO();
        dto.setProcedureResultId(entity.getProcedureResultId());
        dto.setStatus(entity.getStatus());
        dto.setPerformedAt(entity.getPerformedAt());
        dto.setPerformerId(entity.getPerformerId());
        dto.setDetail(entity.getDetail());
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

    private void validateReceptionRecordRequest(RecordRequestDTO recordRequestDTO) {
        Long receptionId = recordRequestDTO.getReceptionId();
        if (receptionId == null) {
            throw new RecordReceptionValidationException("접수 정보 검증 실패: receptionId는 필수입니다.");
        }

        String requestPatientName = trimToNull(recordRequestDTO.getPatientName());
        String requestDepartmentName = trimToNull(recordRequestDTO.getDepartmentName());
        List<String> validationErrors = new ArrayList<>();

        if (requestPatientName == null) {
            validationErrors.add("환자명 값이 없습니다");
        }
        if (requestDepartmentName == null) {
            validationErrors.add("진료과 값이 없습니다");
        }
        if (!validationErrors.isEmpty()) {
            throw new RecordReceptionValidationException("접수 정보 검증 실패: " + String.join(", ", validationErrors));
        }

        OutpatientReceptionDTO receptionDetail;
        try {
            receptionDetail = receptionIntegrationService.findDetail(receptionId);
        } catch (ResponseStatusException ex) {
            if (HttpStatus.NOT_FOUND.equals(ex.getStatus())) {
                throw new RecordReceptionValidationException("접수 정보 검증 실패: 유효하지 않은 receptionId입니다. receptionId=" + receptionId);
            }
            throw new RecordReceptionLookupException(
                    ex.getStatus(),
                    firstNonBlank(ex.getReason(), "접수 상세 조회에 실패했습니다.")
            );
        }

        String actualPatientName = trimToNull(receptionDetail.getPatientName());
        String actualDepartmentName = trimToNull(receptionDetail.getDepartmentName());
        List<String> mismatchErrors = new ArrayList<>();

        if (!requestPatientName.equals(actualPatientName)) {
            mismatchErrors.add("환자명 불일치");
        }
        if (!requestDepartmentName.equals(actualDepartmentName)) {
            mismatchErrors.add("진료과 불일치");
        }

        if (!mismatchErrors.isEmpty()) {
            throw new RecordReceptionValidationException("접수 정보 검증 실패: " + String.join(", ", mismatchErrors));
        }
    }

    private String trimToNull(String value) {
        if (!hasText(value)) {
            return null;
        }
        return value.trim();
    }

    private String firstNonBlank(String first, String second) {
        String firstValue = trimToNull(first);
        if (firstValue != null) {
            return firstValue;
        }
        return trimToNull(second);
    }

    private String createRecordId() {
        return "REC_" + System.currentTimeMillis();
    }

    private String createMedicationId() {
        return "MED_" + System.currentTimeMillis();
    }

    private String createTreatmentResultId() {
        return "PROC_" + System.currentTimeMillis();
    }
}
