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


    //@Todo for project we will use this, right now we are going to REST TEMPLATE 3

//    @Gateway(requestChannel = "req-po-channel", replyChannel = "rep-po-channel")
//    Object createPurchaseOrder(@Payload PurchaseOrderAcceptDTO po);
}
