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



    public OutpatientReceptionDTO findDetail(Long id) {
        return receptionApiClient.fetchDetail(id);
    }}
