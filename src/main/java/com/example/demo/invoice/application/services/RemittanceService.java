package com.example.demo.invoice.application.services;

import com.example.demo.invoice.application.dto.RemittanceDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RemittanceService {

    @Autowired
    OutBoundGateways outBoundGateways;

    @Autowired
    @Qualifier("objectMapper")
    ObjectMapper mapper;

    public void sendRemittanceHTTP(RemittanceDTO remittanceDTO){
        String json = null;
        try {
            json = mapper.writeValueAsString(remittanceDTO);
            outBoundGateways.sendRemittance(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
