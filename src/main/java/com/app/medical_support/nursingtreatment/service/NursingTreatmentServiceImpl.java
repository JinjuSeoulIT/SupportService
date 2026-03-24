package com.app.medical_support.nursingtreatment.service;

import com.app.medical_support.nursingtreatment.dto.MedicationRecordDTO;
import com.app.medical_support.nursingtreatment.dto.RecordDTO;
import com.app.medical_support.nursingtreatment.dto.RecordRequestDTO;
import com.app.medical_support.nursingtreatment.dto.RecordResponseDTO;
import com.app.medical_support.nursingtreatment.dto.TreatmentResultDTO;
import com.app.medical_support.nursingtreatment.entity.MedicationRecordEntity;
import com.app.medical_support.nursingtreatment.entity.RecordEntity;
import com.app.medical_support.nursingtreatment.entity.TreatmentResultEntity;
import com.app.medical_support.nursingtreatment.exception.MedicationRecordNotFoundException;
import com.app.medical_support.nursingtreatment.exception.RecordNotFoundException;
import com.app.medical_support.nursingtreatment.exception.TreatmentResultNotFoundException;
import com.app.medical_support.nursingtreatment.mapper.RecordMapper;
import com.app.medical_support.nursingtreatment.mapstruct.RecordReqMapStruct;
import com.app.medical_support.nursingtreatment.mapstruct.RecordResMapStruct;
import com.app.medical_support.nursingtreatment.repository.MedicationRecordRepository;
import com.app.medical_support.nursingtreatment.repository.RecordRepository;
import com.app.medical_support.nursingtreatment.repository.TreatmentResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    @Override
    public List<RecordResponseDTO> search(String searchType, String searchValue, String startDate, String endDate) {
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
        RecordEntity entity = recordReqMapStruct.toEntity(recordRequestDTO);

        if (!hasText(entity.getRecordId())) {
            entity.setRecordId(createRecordId());
        }

        if (!hasText(entity.getNursingId())) {
            entity.setNursingId("NUR_" + System.currentTimeMillis());
        }

        entity.setStatus("ACTIVE");
        entity.setCreatedAt(LocalDateTime.now());
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
        saved.setStatus(recordDTO.getStatus());
        saved.setVisitId(recordDTO.getVisitId());
        saved.setNursingId(recordDTO.getNursingId());
        saved.setUpdatedAt(LocalDateTime.now());

        return recordResMapStruct.toDTO(recordRepository.save(saved));
    }

    @Override
    @Transactional
    public RecordDTO updateRecordStatus(String id, String status) {
        RecordEntity entity = recordRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));

        entity.setStatus(status);
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
        entity.setOrderItemId(medicationRecordDTO.getOrderItemId());
        entity.setAdministeredAt(medicationRecordDTO.getAdministeredAt());
        entity.setDoseNumber(medicationRecordDTO.getDoseNumber());
        entity.setDoseUnit(medicationRecordDTO.getDoseUnit());
        entity.setNurseId(medicationRecordDTO.getNurseId());
        entity.setStatus(normalizeStatus(medicationRecordDTO.getStatus()));
        return toMedicationRecordDTO(medicationRecordRepository.save(entity));
    }

    @Override
    @Transactional
    public MedicationRecordDTO modifyMedicationRecord(String id, MedicationRecordDTO medicationRecordDTO) {
        MedicationRecordEntity entity = medicationRecordRepository.findById(id)
                .orElseThrow(() -> new MedicationRecordNotFoundException(id));
        entity.setOrderItemId(medicationRecordDTO.getOrderItemId());
        entity.setAdministeredAt(medicationRecordDTO.getAdministeredAt());
        entity.setDoseNumber(medicationRecordDTO.getDoseNumber());
        entity.setDoseUnit(medicationRecordDTO.getDoseUnit());
        entity.setNurseId(medicationRecordDTO.getNurseId());
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
        entity.setOrderItemId(treatmentResultDTO.getOrderItemId());
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
        entity.setOrderItemId(treatmentResultDTO.getOrderItemId());
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
        dto.setOrderItemId(entity.getOrderItemId());
        dto.setAdministeredAt(entity.getAdministeredAt());
        dto.setDoseNumber(entity.getDoseNumber());
        dto.setDoseUnit(entity.getDoseUnit());
        dto.setNurseId(entity.getNurseId());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    private TreatmentResultDTO toTreatmentResultDTO(TreatmentResultEntity entity) {
        TreatmentResultDTO dto = new TreatmentResultDTO();
        dto.setProcedureResultId(entity.getProcedureResultId());
        dto.setOrderItemId(entity.getOrderItemId());
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
