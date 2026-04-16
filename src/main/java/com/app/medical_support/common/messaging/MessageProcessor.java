package com.app.medical_support.common.messaging;

import com.app.medical_support.common.event.Event;
import com.app.medical_support.common.exceptions.EventProcessingException;
import com.app.medical_support.diagnosticexecution.dto.TestExecutionReqDTO;
import com.app.medical_support.diagnosticexecution.service.DiagnosticExecutionService;
import com.app.medical_support.nursingtreatment.dto.MedicationRecordReqDTO;
import com.app.medical_support.nursingtreatment.dto.TreatmentResultCreateDTO;
import com.app.medical_support.nursingtreatment.service.NursingTreatmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
public class MessageProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(MessageProcessor.class);

    @Bean
    public Consumer<Message<Event<String, MedicationRecordReqDTO>>> messageProcessorMedicationRecord(
            NursingTreatmentService nursingTreatmentService
    ) {
        return message -> {
            Event<String, MedicationRecordReqDTO> event = message.getPayload();
            LOG.info("Process message created at {}...", event.getEventCreatedAt());

            switch (event.getEventType()) {
                case CREATE:
                    MedicationRecordReqDTO dto = event.getData();
                    String medicationId = dto != null ? dto.getMedicationId() : event.getKey();
                    LOG.info("Create medicationRecord with ID: {}", medicationId);
                    nursingTreatmentService.registerMedicationRecord(dto);
                    break;
                case DELETE:
                    String medicationIdForDelete = event.getKey();
                    LOG.info("Delete medicationRecord with ID: {}", medicationIdForDelete);
                    nursingTreatmentService.updateMedicationRecordStatus(medicationIdForDelete, "INACTIVE");
                    break;
                default:
                    String errorMessage =
                            "Incorrect event type: " + event.getEventType() + ", expected a CREATE or DELETE event";
                    LOG.warn(errorMessage);
                    throw new EventProcessingException(errorMessage);
            }

            LOG.info("Message processing done!");
        };
    }

    @Bean
    public Consumer<Message<Event<String, TreatmentResultCreateDTO>>> messageProcessorTreatmentResult(
            NursingTreatmentService nursingTreatmentService
    ) {
        return message -> {
            Event<String, TreatmentResultCreateDTO> event = message.getPayload();
            LOG.info("Process message created at {}...", event.getEventCreatedAt());

            switch (event.getEventType()) {
                case CREATE:
                    TreatmentResultCreateDTO dto = event.getData();
                    String procedureResultId = dto != null ? dto.getProcedureResultId() : event.getKey();
                    LOG.info("Create treatmentResult with ID: {}", procedureResultId);
                    nursingTreatmentService.registerTreatmentResult(dto);
                    break;
                case DELETE:
                    String procedureResultIdForDelete = event.getKey();
                    LOG.info("Delete treatmentResult with ID: {}", procedureResultIdForDelete);
                    nursingTreatmentService.updateTreatmentResultStatus(procedureResultIdForDelete, "INACTIVE");
                    break;
                default:
                    String errorMessage =
                            "Incorrect event type: " + event.getEventType() + ", expected a CREATE or DELETE event";
                    LOG.warn(errorMessage);
                    throw new EventProcessingException(errorMessage);
            }

            LOG.info("Message processing done!");
        };
    }

    @Bean
    public Consumer<Message<Event<Long, TestExecutionReqDTO>>> messageProcessorTestExecution(
            DiagnosticExecutionService diagnosticExecutionService
    ) {
        return message -> {
            Event<Long, TestExecutionReqDTO> event = message.getPayload();
            LOG.info("Process message created at {}...", event.getEventCreatedAt());

            switch (event.getEventType()) {
                case CREATE:
                    TestExecutionReqDTO dto = event.getData();
                    Long orderItemId = event.getKey();
                    LOG.info("Create testExecution with OrderItemID: {}", orderItemId);
                    if (dto != null && dto.getOrderItemId() == null && orderItemId != null) {
                        dto.setOrderItemId(orderItemId);
                    }
                    diagnosticExecutionService.registerTestExecution(dto);
                    break;
                case DELETE:
                    Long orderItemIdForDelete = event.getKey();
                    LOG.info("Delete testExecution with OrderItemID: {}", orderItemIdForDelete);
                    // NOTE: 현재 DiagnosticExecutionService에는 testExecution delete가 없어 무시합니다.
                    break;
                default:
                    String errorMessage =
                            "Incorrect event type: " + event.getEventType() + ", expected a CREATE or DELETE event";
                    LOG.warn(errorMessage);
                    throw new EventProcessingException(errorMessage);
            }

            LOG.info("Message processing done!");
        };
    }
}

