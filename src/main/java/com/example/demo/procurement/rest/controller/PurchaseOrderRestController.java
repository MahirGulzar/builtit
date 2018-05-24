package com.example.demo.procurement.rest.controller;

import com.example.demo.procurement.application.dto.PurchaseOrderDTO;
import com.example.demo.procurement.application.dto.PurchaseOrderSupplierDTO;
import com.example.demo.procurement.application.services.ProcurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/procurements/orders")
@CrossOrigin
public class PurchaseOrderRestController {

    @Autowired
    ProcurementService procurementService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<PurchaseOrderDTO> findAllPurcahseOrders() {
        return procurementService.findAllPurchaseOrder();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PurchaseOrderSupplierDTO findOnePurchaseOrder(@PathVariable("id") Long id) {
        return procurementService.findOnePurchaseOrder(id);
    }

}