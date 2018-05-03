package com.example.demo.procurement.rest.controller;

import com.example.demo.procurement.application.dto.PurchaseOrderDTO;
import com.example.demo.procurement.application.services.ProcurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
//
//@RestController
//@RequestMapping("admin/api/orders")
//@CrossOrigin
//public class PurchaseOrderRestController {
//
//    @Autowired
//    ProcurementService procurementService;
//
//    @GetMapping()
//    @ResponseStatus(HttpStatus.OK)
//    public List<PurchaseOrderDTO> findAllPO() {
//        return procurementService.findAllPurchaseOrder();
//    }
//
//    @GetMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public PurchaseOrderDTO findOnePO(@PathVariable("id") String id) {
//        return procurementService.findOnePurchaseOrder(id);
//    }
//
//    @PostMapping("/{id}/accept")
//    public ResponseEntity<PurchaseOrderDTO> acceptPO(@PathVariable("id") String id) throws Exception {
//        PurchaseOrderDTO poDTO = procurementService.acceptPurchaseOrder(id);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(new URI(poDTO.getId().getHref()));
//        return new ResponseEntity<>(poDTO, headers, HttpStatus.OK);
//    }
//}