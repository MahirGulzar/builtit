package com.example.demo.procurement.integration.gateways;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@MessagingGateway
public interface RentalGateway {
    @Gateway(requestChannel = "req-channel", replyChannel = "rep-channel")
    Object findPlants(@Payload String name, @Header("startDate") LocalDate startDate, @Header("endDate") LocalDate endDate);

    @Gateway(requestChannel = "req-po-channel")
    Object createPurchaseOrder(@Payload String po, @Header("url") String url);

    @Gateway(requestChannel = "supplier-po-cancel-channel")
    Object requestSupplierForPOCancellation(@Payload String po, @Header("url") String url);

    @Gateway(requestChannel = "supplier-po-channel")
    Object getSupplierPO(@Payload String url);

    @Gateway(requestChannel = "req-plant-channel")
    Object getPlantHTTP(@Payload String url);

    @Gateway(requestChannel = "supplier-po-extension-channel")
    Object requestPOExtension(@Payload String json, @Header("url") String url);


}
