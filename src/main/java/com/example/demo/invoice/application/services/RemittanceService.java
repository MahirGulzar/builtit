package com.example.demo.invoice.application.services;

import com.example.demo.invoice.application.dto.RemittanceDTO;
import com.example.demo.invoice.integration.gateways.RemittanceGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RemittanceService {

    @Autowired
    RemittanceGateway remittanceGateway;

    @Autowired
    @Qualifier("objectMapper")
    ObjectMapper mapper;

    public void sendRemittanceHTTP(RemittanceDTO remittanceDTO){
        String json = null;
        try {
            json = mapper.writeValueAsString(remittanceDTO);
            remittanceGateway.sendRemittance(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
