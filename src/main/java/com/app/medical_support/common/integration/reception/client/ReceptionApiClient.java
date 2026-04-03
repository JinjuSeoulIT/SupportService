package com.app.medical_support.common.integration.reception.client;

import com.app.medical_support.common.ApiResponse;
import com.app.medical_support.common.integration.reception.dto.OutpatientReceptionDTO;
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
import java.util.List;

@Component
public class ReceptionApiClient {

    private static final String DEFAULT_BASE_URL = "http://192.168.1.55:8283";

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public ReceptionApiClient(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${integration.reception.base-url:" + DEFAULT_BASE_URL + "}") String baseUrl
    ) {
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(3))
                .setReadTimeout(Duration.ofSeconds(5))
                .build();
        this.baseUrl = normalizeBaseUrl(baseUrl);
    }

    public OutpatientReceptionDTO fetchDetail(Long id) {
        String uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/api/receptions/{id}")
                .buildAndExpand(id)
                .toUriString();

        try {
            ResponseEntity<ApiResponse<OutpatientReceptionDTO>> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ApiResponse<OutpatientReceptionDTO>>() {
                    }
            );

            return unwrapResult(responseEntity.getBody(), "Reception detail fetch failed.");
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reception not found. id=" + id, ex);
        } catch (HttpClientErrorException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reception detail request failed.", ex);
        } catch (HttpServerErrorException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Reception service failed.", ex);
        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Reception service is unreachable.", ex);
        } catch (RestClientException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Reception service call failed.", ex);
        }
    }

    private <T> T unwrapResult(ApiResponse<T> response, String defaultMessage) {
        if (response == null) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Reception service response is empty.");
        }

        if (!response.isSuccess()) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, firstNonBlank(response.getMessage(), defaultMessage));
        }

        T result = response.getResult();
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, defaultMessage);
        }

        return result;
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
