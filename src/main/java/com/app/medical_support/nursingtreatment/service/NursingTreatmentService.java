package com.app.medical_support.nursingtreatment.service;

import com.app.medical_support.nursingtreatment.dto.MedicationRecordDTO;
import com.app.medical_support.nursingtreatment.dto.MedicationRecordReqDTO;
import com.app.medical_support.nursingtreatment.dto.RecordDTO;
import com.app.medical_support.nursingtreatment.dto.RecordRequestDTO;
import com.app.medical_support.nursingtreatment.dto.RecordResponseDTO;
import com.app.medical_support.nursingtreatment.dto.TreatmentResultDTO;
import com.app.medical_support.nursingtreatment.dto.TreatmentResultReqDTO;

import java.util.List;

public interface NursingTreatmentService {
    List<RecordResponseDTO> search(String searchType, String searchValue, String startDate, String endDate);
    List<RecordResponseDTO> findRecordList();
    RecordResponseDTO findRecordDetail(String nursingId);
    RecordDTO registerRecord(RecordRequestDTO recordRequestDTO);
    RecordDTO modifyRecord(String nursingId, RecordDTO recordDTO);
    RecordDTO updateRecordStatus(String nursingId, String status);

    List<MedicationRecordDTO> findMedicationRecordList();
    MedicationRecordDTO findMedicationRecordDetail(String id);
    MedicationRecordDTO registerMedicationRecord(MedicationRecordReqDTO medicationRecordDTO);
    MedicationRecordDTO modifyMedicationRecord(String id, MedicationRecordReqDTO medicationRecordDTO);
    MedicationRecordDTO updateMedicationRecordStatus(String id, String status);

    List<TreatmentResultDTO> findTreatmentResultList();
    TreatmentResultDTO findTreatmentResultDetail(String id);
    TreatmentResultDTO registerTreatmentResult(TreatmentResultReqDTO treatmentResultDTO);
    TreatmentResultDTO modifyTreatmentResult(String id, TreatmentResultReqDTO treatmentResultDTO);
    TreatmentResultDTO updateTreatmentResultStatus(String id, String status);
}
