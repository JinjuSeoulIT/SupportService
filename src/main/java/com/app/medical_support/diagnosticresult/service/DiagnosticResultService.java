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

import java.util.List;

public interface DiagnosticResultService {

    List<ImagingResultDTO> findImagingResultList();
    ImagingResultDTO findImagingResultDetail(String id);
    ImagingResultDTO registerImagingResult(ImagingResultCreateReqDTO dto);
    ImagingResultDTO modifyImagingResult(String id, ImagingResultUpdateReqDTO dto);
    void deleteImagingResult(String id);

    List<EndoscopyResultDTO> findEndoscopyResultList();
    EndoscopyResultDTO findEndoscopyResultDetail(String id);
    EndoscopyResultDTO registerEndoscopyResult(EndoscopyResultCreateReqDTO dto);
    EndoscopyResultDTO modifyEndoscopyResult(String id, EndoscopyResultUpdateReqDTO dto);
    void deleteEndoscopyResult(String id);

    List<PathologyResultDTO> findPathologyResultList();
    PathologyResultDTO findPathologyResultDetail(String id);
    PathologyResultDTO registerPathologyResult(PathologyResultCreateReqDTO dto);
    PathologyResultDTO modifyPathologyResult(String id, PathologyResultUpdateReqDTO dto);
    void deletePathologyResult(String id);

    List<PhysiologicalResultDTO> findPhysiologicalResultList();
    PhysiologicalResultDTO findPhysiologicalResultDetail(String id);
    PhysiologicalResultDTO registerPhysiologicalResult(PhysiologicalResultCreateReqDTO dto);
    PhysiologicalResultDTO modifyPhysiologicalResult(String id, PhysiologicalResultUpdateReqDTO dto);
    void deletePhysiologicalResult(String id);

    List<SpecimenTestResultDTO> findSpecimenResultList();
    SpecimenTestResultDTO findSpecimenResultDetail(String id);
    SpecimenTestResultDTO registerSpecimenResult(SpecimenTestResultCreateReqDTO dto);
    SpecimenTestResultDTO modifySpecimenResult(String id, SpecimenTestResultUpdateReqDTO dto);
    void deleteSpecimenResult(String id);
}
