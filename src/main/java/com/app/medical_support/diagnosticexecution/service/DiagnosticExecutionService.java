package com.app.medical_support.diagnosticexecution.service;

import com.app.medical_support.diagnosticexecution.dto.EndoscopyCreateReqDTO;
import com.app.medical_support.diagnosticexecution.dto.EndoscopyDTO;
import com.app.medical_support.diagnosticexecution.dto.ImagingCreateReqDTO;
import com.app.medical_support.diagnosticexecution.dto.ImagingDTO;
import com.app.medical_support.diagnosticexecution.dto.PathologyCreateReqDTO;
import com.app.medical_support.diagnosticexecution.dto.PathologyDTO;
import com.app.medical_support.diagnosticexecution.dto.PhysiologicalCreateReqDTO;
import com.app.medical_support.diagnosticexecution.dto.PhysiologicalDTO;
import com.app.medical_support.diagnosticexecution.dto.SpecimenCreateReqDTO;
import com.app.medical_support.diagnosticexecution.dto.SpecimenDTO;
import com.app.medical_support.diagnosticexecution.dto.TestExecutionDTO;
import com.app.medical_support.diagnosticexecution.dto.TestExecutionReqDTO;
import com.app.medical_support.diagnosticexecution.dto.TestExecutionUpdateDTO;
import com.app.medical_support.diagnosticexecution.entity.TestExecutionEntity;

import java.util.List;

public interface DiagnosticExecutionService {

    List<ImagingDTO> findImagingList();
    ImagingDTO findImagingDetail(String id);
    ImagingDTO registerImaging(ImagingCreateReqDTO imagingDTO);
    ImagingDTO modifyImaging(String id, ImagingDTO imagingDTO);
    void deleteImaging(String id);

    List<EndoscopyDTO> findEndoscopyList();
    EndoscopyDTO findEndoscopyDetail(String id);
    EndoscopyDTO registerEndoscopy(EndoscopyCreateReqDTO endoscopyDTO);
    EndoscopyDTO modifyEndoscopy(String id, EndoscopyDTO endoscopyDTO);
    void deleteEndoscopy(String id);

    List<PathologyDTO> findPathologyList();
    PathologyDTO findPathologyDetail(String id);
    PathologyDTO registerPathology(PathologyCreateReqDTO pathologyDTO);
    PathologyDTO modifyPathology(String id, PathologyDTO pathologyDTO);
    void deletePathology(String id);

    List<PhysiologicalDTO> findPhysiologicalList();
    PhysiologicalDTO findPhysiologicalDetail(String id);
    PhysiologicalDTO registerPhysiological(PhysiologicalCreateReqDTO physiologicalDTO);
    PhysiologicalDTO modifyPhysiological(String id, PhysiologicalDTO physiologicalDTO);
    void deletePhysiological(String id);

    List<SpecimenDTO> searchSpecimen(String searchType, String searchValue);
    List<SpecimenDTO> findSpecimenList();
    SpecimenDTO findSpecimenDetail(String id);
    SpecimenDTO registerSpecimen(SpecimenCreateReqDTO specimenDTO);
    SpecimenDTO modifySpecimen(String id, SpecimenDTO specimenDTO);
    void deleteSpecimen(String id);

    List<TestExecutionDTO> findTestExecutionList(String executionType);
    TestExecutionDTO findTestExecutionDetail(String id);
    TestExecutionDTO registerTestExecution(TestExecutionReqDTO testExecutionDTO);
    TestExecutionDTO modifyTestExecution(String id, TestExecutionUpdateDTO testExecutionUpdateDTO);
}
