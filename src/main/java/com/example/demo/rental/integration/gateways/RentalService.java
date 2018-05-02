package com.example.demo.rental.integration.gateways;

import com.example.demo.procurement.application.dto.PlantInventoryEntryDTO;
import com.example.demo.procurement.application.dto.PurchaseOrderAcceptDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@MessagingGateway
public interface RentalService {
    @Gateway(requestChannel = "req-channel", replyChannel = "rep-channel")
    Object findPlants(@Payload String name, @Header("startDate") LocalDate startDate, @Header("endDate") LocalDate endDate);


    //@Todo for project we will use this, right now we are going to REST TEMPLATE 3

//    @Gateway(requestChannel = "req-po-channel", replyChannel = "rep-po-channel")
//    Object createPurchaseOrder(@Payload PurchaseOrderAcceptDTO po);
}
