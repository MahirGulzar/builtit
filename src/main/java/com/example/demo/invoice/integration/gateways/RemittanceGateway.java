package com.example.demo.invoice.integration.gateways;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Service;

@IntegrationComponentScan
@Service
@MessagingGateway
public interface RemittanceGateway {
    @Gateway(requestChannel = "sendRemittance")
    public void sendRemittance(String remittance);
}
