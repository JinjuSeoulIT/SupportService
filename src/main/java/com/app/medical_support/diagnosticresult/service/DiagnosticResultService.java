package com.app.medical_support.diagnosticresult.service;

import com.app.medical_support.diagnosticresult.dto.EndoscopyResultDTO;
import com.app.medical_support.diagnosticresult.dto.ImagingResultDTO;
import com.app.medical_support.diagnosticresult.dto.PathologyResultDTO;
import com.app.medical_support.diagnosticresult.dto.PhysiologicalResultDTO;
import com.app.medical_support.diagnosticresult.dto.SpecimenTestResultDTO;

import java.util.List;

public interface DiagnosticResultService {

    List<ImagingResultDTO> findImagingResultList();
    ImagingResultDTO findImagingResultDetail(String id);
    ImagingResultDTO registerImagingResult(ImagingResultDTO dto);
    ImagingResultDTO modifyImagingResult(String id, ImagingResultDTO dto);
    void deleteImagingResult(String id);

    List<EndoscopyResultDTO> findEndoscopyResultList();
    EndoscopyResultDTO findEndoscopyResultDetail(String id);
    EndoscopyResultDTO registerEndoscopyResult(EndoscopyResultDTO dto);
    EndoscopyResultDTO modifyEndoscopyResult(String id, EndoscopyResultDTO dto);
    void deleteEndoscopyResult(String id);

    List<PathologyResultDTO> findPathologyResultList();
    PathologyResultDTO findPathologyResultDetail(String id);
    PathologyResultDTO registerPathologyResult(PathologyResultDTO dto);
    PathologyResultDTO modifyPathologyResult(String id, PathologyResultDTO dto);
    void deletePathologyResult(String id);

    List<PhysiologicalResultDTO> findPhysiologicalResultList();
    PhysiologicalResultDTO findPhysiologicalResultDetail(String id);
    PhysiologicalResultDTO registerPhysiologicalResult(PhysiologicalResultDTO dto);
    PhysiologicalResultDTO modifyPhysiologicalResult(String id, PhysiologicalResultDTO dto);
    void deletePhysiologicalResult(String id);

    List<SpecimenTestResultDTO> findSpecimenResultList();
    SpecimenTestResultDTO findSpecimenResultDetail(String id);
    SpecimenTestResultDTO registerSpecimenResult(SpecimenTestResultDTO dto);
    SpecimenTestResultDTO modifySpecimenResult(String id, SpecimenTestResultDTO dto);
    void deleteSpecimenResult(String id);
}
