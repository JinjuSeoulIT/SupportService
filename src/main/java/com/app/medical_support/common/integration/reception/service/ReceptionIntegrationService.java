package com.app.medical_support.common.integration.reception.service;

import com.app.medical_support.common.integration.reception.client.ReceptionApiClient;
import com.app.medical_support.common.integration.reception.dto.OutpatientReceptionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReceptionIntegrationService {

    private final ReceptionApiClient receptionApiClient;

    public List<OutpatientReceptionDTO> findListByConditions(
            String visitDate,
            String visitType,
            String statuses
    ) {
        LocalDate targetDate = parseVisitDate(visitDate);
        String normalizedVisitType = normalizeVisitType(visitType);
        String normalizedStatuses = normalizeStatuses(statuses);

        return receptionApiClient.fetchListByConditions(
                targetDate.toString(),
                normalizedVisitType,
                normalizedStatuses
        );
    }

    public OutpatientReceptionDTO findDetail(Long id) {
        return receptionApiClient.fetchDetail(id);
    }

    private LocalDate parseVisitDate(String visitDate) {
        String value = trimToNull(visitDate);
        if (value == null) {
            throw new ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "visitDate is required. format=yyyy-MM-dd"
            );
        }

        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException ex) {
            throw new ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "visitDate format is invalid. format=yyyy-MM-dd",
                    ex
            );
        }
    }

    private String normalizeVisitType(String visitType) {
        String value = trimToNull(visitType);
        return value == null ? "OUTPATIENT" : value.toUpperCase();
    }

    private String normalizeStatuses(String statuses) {
        String value = trimToNull(statuses);
        if (value == null) {
            return "WAITING,CALLED,IN_PROGRESS";
        }

        String normalized = Arrays.stream(value.split(","))
                .map(this::trimToNull)
                .filter(item -> item != null && !item.isEmpty())
                .map(String::toUpperCase)
                .collect(Collectors.joining(","));

        if (normalized.isEmpty()) {
            throw new ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "statuses is invalid. use CSV format e.g. WAITING,CALLED,IN_PROGRESS"
            );
        }
        return normalized;
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
