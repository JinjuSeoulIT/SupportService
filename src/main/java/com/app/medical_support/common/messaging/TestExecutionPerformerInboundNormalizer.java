package com.app.medical_support.common.messaging;

import com.app.medical_support.diagnosticexecution.dto.TestExecutionReqDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 진료 Kafka에서 내려오는 검사 수행 등록 시, {@code performerId}에 처방 의사 ID가 실리는 경우가 있어
 * 수신 단계에서 제거한다. 검사 수행/접수 담당은 검사실에서 이후 입력한다.
 *
 * <p>제거 대상: (1) 직원 코드 {@code DOC…} 접두, (2) 숫자만 있는 문자열(진료에서 {@code Long} 처방의 ID를 넣는 경우).
 */
@Component
public class TestExecutionPerformerInboundNormalizer {

    private static final Logger LOG = LoggerFactory.getLogger(TestExecutionPerformerInboundNormalizer.class);

    public void applyForClinicalKafka(TestExecutionReqDTO dto) {
        if (dto == null) {
            return;
        }
        String raw = dto.getPerformerId();
        if (raw == null) {
            return;
        }
        String trimmed = raw.trim();
        if (trimmed.isEmpty()) {
            dto.setPerformerId(null);
            return;
        }
        if (shouldIgnoreClinicalPerformerId(trimmed)) {
            LOG.debug("Ignoring clinical performerId (doctor/order id, not lab performer): {}", trimmed);
            dto.setPerformerId(null);
        }
    }

    /** 접수·직원 마스터 {@code DOC-…} 형태 */
    private static boolean isDocStaffCodePrefix(String s) {
        return s.length() >= 3 && s.regionMatches(true, 0, "DOC", 0, 3);
    }

    /** 진료 측에서 처방 의사 {@link Long} 을 문자열로 넣는 경우 */
    private static boolean isDigitsOnly(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return !s.isEmpty();
    }

    private static boolean shouldIgnoreClinicalPerformerId(String trimmed) {
        return isDocStaffCodePrefix(trimmed) || isDigitsOnly(trimmed);
    }
}
