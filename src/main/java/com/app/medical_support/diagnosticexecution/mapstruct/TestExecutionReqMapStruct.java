package com.app.medical_support.diagnosticexecution.mapstruct;

import com.app.medical_support.common.mapstruct.ReqMapStruct;
import com.app.medical_support.diagnosticexecution.dto.TestExecutionDTO;
import com.app.medical_support.diagnosticexecution.entity.TestExecutionEntity;
import com.app.medical_support.nursingtreatment.dto.RecordRequestDTO;
import com.app.medical_support.nursingtreatment.entity.RecordEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TestExecutionReqMapStruct  extends ReqMapStruct<TestExecutionEntity, TestExecutionDTO> {

    TestExecutionEntity toEntity(TestExecutionDTO dto) ;


}
