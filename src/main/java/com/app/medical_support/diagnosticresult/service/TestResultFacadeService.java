package com.app.medical_support.diagnosticresult.service;

import com.app.medical_support.diagnosticresult.dto.TestResultListDTO;
import com.app.medical_support.diagnosticresult.dto.TestResultDetailDTO;
import com.app.medical_support.diagnosticresult.dto.TestResultSearchCondition;
import com.app.medical_support.diagnosticresult.dto.TestResultUpdateReqDTO;

import java.util.List;

public interface TestResultFacadeService {

    List<TestResultListDTO> findTestResultList(TestResultSearchCondition condition);

    TestResultDetailDTO findTestResultDetail(String resultType, String resultId);

    TestResultDetailDTO modifyTestResult(String resultType, String resultId, TestResultUpdateReqDTO dto);
}
