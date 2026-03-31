package com.app.medical_support.common.integration.reception.service;

import com.app.medical_support.common.integration.reception.client.ReceptionApiClient;
import com.app.medical_support.common.integration.reception.dto.OutpatientReceptionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceptionIntegrationService {

    private final ReceptionApiClient receptionApiClient;

    public List<OutpatientReceptionDTO> findWaitingList(Long departmentId, Long doctorId, String date) {
        return receptionApiClient.fetchQueue(departmentId, doctorId, date).stream()
                .filter(this::isWaitingStatus)
                .toList();
    }

    public OutpatientReceptionDTO findDetail(Long id) {
        return receptionApiClient.fetchDetail(id);
    }

    private boolean isWaitingStatus(OutpatientReceptionDTO dto) {
        if (dto == null) {
            return false;
        }

        String status = trimToNull(dto.getStatus());
        if (status == null) {
            return false;
        }

        return "WAITING".equalsIgnoreCase(status) || "대기".equals(status);
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
