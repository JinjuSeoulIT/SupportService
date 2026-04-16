package com.app.medical_support.common.integration.clinical.client;

import com.app.medical_support.common.ApiResponse;
import com.app.medical_support.common.integration.clinical.dto.ClinicalVitalAssessResponse;
import com.app.medical_support.common.integration.clinical.dto.ClinicalVisitSummaryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Component
public class ClinicalApiClient {

    private static final String DEFAULT_BASE_URL = "http://192.168.1.70:8090";

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public ClinicalApiClient(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${integration.clinical.base-url:" + DEFAULT_BASE_URL + "}") String baseUrl
    ) {
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(3))
                .setReadTimeout(Duration.ofSeconds(5))
                .build();
        this.baseUrl = normalizeBaseUrl(baseUrl);
    }

    public List<ClinicalVisitSummaryResponse> fetchVisitList() {
        String uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/api/visits")
                .toUriString();
        try {
            ResponseEntity<ApiResponse<List<ClinicalVisitSummaryResponse>>> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ApiResponse<List<ClinicalVisitSummaryResponse>>>() {
                    }
            );
            return unwrapList(responseEntity.getBody(), "Clinical visit list fetch failed.");
        } catch (HttpClientErrorException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Clinical visit list request failed.", ex);
        } catch (HttpServerErrorException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Clinical service failed.", ex);
        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Clinical service is unreachable.", ex);
        } catch (RestClientException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Clinical service call failed.", ex);
        }
    }

    public ClinicalVitalAssessResponse fetchVitalAssess(Long visitId) {
        String uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/api/visits/{visitId}/vital-assess")
                .buildAndExpand(visitId)
                .toUriString();
        try {
            ResponseEntity<ApiResponse<ClinicalVitalAssessResponse>> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ApiResponse<ClinicalVitalAssessResponse>>() {
                    }
            );
            return unwrapAllowNull(responseEntity.getBody(), "Clinical vital assess fetch failed.");
        } catch (HttpClientErrorException.NotFound ex) {
            return null;
        } catch (HttpClientErrorException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Clinical vital assess request failed.", ex);
        } catch (HttpServerErrorException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Clinical service failed.", ex);
        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Clinical service is unreachable.", ex);
        } catch (RestClientException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Clinical service call failed.", ex);
        }
    }

    private List<ClinicalVisitSummaryResponse> unwrapList(
            ApiResponse<List<ClinicalVisitSummaryResponse>> response,
            String defaultMessage
    ) {
        if (response == null) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Clinical service response is empty.");
        }
        if (!response.isSuccess()) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, firstNonBlank(response.getMessage(), defaultMessage));
        }
        List<ClinicalVisitSummaryResponse> result = response.getResult();
        return result == null ? Collections.emptyList() : result;
    }

    private ClinicalVitalAssessResponse unwrapAllowNull(
            ApiResponse<ClinicalVitalAssessResponse> response,
            String defaultMessage
    ) {
        if (response == null) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Clinical service response is empty.");
        }
        if (!response.isSuccess()) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, firstNonBlank(response.getMessage(), defaultMessage));
        }
        return response.getResult();
    }

    private String normalizeBaseUrl(String value) {
        String normalized = trimToNull(value);
        if (normalized == null) {
            return DEFAULT_BASE_URL;
        }
        while (normalized.endsWith("/")) {
            normalized = normalized.substring(0, normalized.length() - 1);
        }
        return normalized;
    }

    private String firstNonBlank(String first, String second) {
        String firstValue = trimToNull(first);
        if (firstValue != null) {
            return firstValue;
        }
        return trimToNull(second);
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
