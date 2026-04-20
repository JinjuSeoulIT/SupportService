package com.app.medical_support.integration.outbound.kafka;



import com.app.medical_support.common.event.Event;
import com.app.medical_support.diagnosticexecution.dto.DiagnosticExamOutcomeDTO;
import com.app.medical_support.diagnosticresult.dto.TestResultDetailDTO;
import com.app.medical_support.nursingtreatment.dto.MedicationRecordDTO;
import com.app.medical_support.nursingtreatment.dto.TreatmentResultDTO;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.stream.function.StreamBridge;

import org.springframework.messaging.support.MessageBuilder;

import org.springframework.stereotype.Component;



import static com.app.medical_support.common.event.Event.Type.CREATE;



/**

 * 진료지원 처리 결과를 Kafka로 보냅니다. 구독 예: 진료(검사결과 등 화면), 수납(검사·처치·투약 내역 정산) 등.

 */

@Component

@RequiredArgsConstructor

@Slf4j

public class DownstreamOutcomeEventPublisher {

    /**
     * StreamBridge 바인딩명은 {@code spring.cloud.stream.output-bindings} 에 등록된 논리 이름을 사용합니다.
     * 실제 destination은 {@code spring.cloud.stream.bindings.<name>-out-0.destination} 으로 매핑됩니다.
     */
    public static final String BINDING_OUT_MEDICATION_RECORD_OUTCOME = "output-medicationRecordOutcome";
    public static final String BINDING_OUT_TREATMENT_RESULT_OUTCOME = "output-treatmentResultOutcome";
    public static final String BINDING_OUT_DIAGNOSTIC_EXAM_OUTCOME = "output-diagnosticExamOutcome";
    public static final String BINDING_OUT_DIAGNOSTIC_TEST_RESULT_OUTCOME = "output-diagnosticTestResultOutcome";



    private final StreamBridge streamBridge;

    private final DownstreamKafkaProperties downstreamKafkaProperties;



    public void publishMedicationRecordOutcome(MedicationRecordDTO body) {

        if (!downstreamKafkaProperties.isEnabled() || body == null) {

            return;

        }

        Object key = body.getMedicationRecordId() != null ? body.getMedicationRecordId() : body.getMedicationId();

        streamBridge.send(

                BINDING_OUT_MEDICATION_RECORD_OUTCOME,

                MessageBuilder.withPayload(new Event<>(CREATE, key, body)).build());

    }



    public void publishTreatmentResultOutcome(TreatmentResultDTO body) {

        if (!downstreamKafkaProperties.isEnabled() || body == null) {

            return;

        }

        Object key = body.getTreatmentResultId() != null ? body.getTreatmentResultId() : body.getProcedureResultId();

        streamBridge.send(

                BINDING_OUT_TREATMENT_RESULT_OUTCOME,

                MessageBuilder.withPayload(new Event<>(CREATE, key, body)).build());

    }

    /**
     * 검사(실행) 진행상태가 {@code COMPLETED}로 전환되는 시점에 발행합니다.
     */
    public void publishDiagnosticExamOutcome(DiagnosticExamOutcomeDTO body) {
        if (!downstreamKafkaProperties.isEnabled() || body == null) {
            return;
        }
        Object key = body.getExamId() != null ? body.getExamId() : body.getTestExecutionId();
        streamBridge.send(
                BINDING_OUT_DIAGNOSTIC_EXAM_OUTCOME,
                MessageBuilder.withPayload(new Event<>(CREATE, key, body)).build());
    }



    /**

     * 검사 결과가 {@code COMPLETED}로 확정된 뒤 발행합니다. 페이로드는 검사 유형별 상세를 포함합니다.

     */

    public void publishDiagnosticTestResultOutcome(TestResultDetailDTO body) {

        if (!downstreamKafkaProperties.isEnabled() || body == null) {

            return;

        }

        Object key = body.getResultId() != null ? body.getResultId() : body.getTestExecutionId();

        streamBridge.send(

                BINDING_OUT_DIAGNOSTIC_TEST_RESULT_OUTCOME,

                MessageBuilder.withPayload(new Event<>(CREATE, key, body)).build());

    }

}

