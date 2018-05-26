package com.example.demo.invoice.rest.controller;

import com.example.demo.invoice.application.dto.InvoiceDTO;
import com.example.demo.invoice.application.services.InvoiceService;
import com.example.demo.procurement.application.dto.PlantHireRequest.PlantHireRequestDTO;
import com.example.demo.procurement.application.dto.PurchaseOrderSupplierDTO;
import com.example.demo.procurement.application.services.ProcurementService;
import gherkin.deps.com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/invoices")
@CrossOrigin
public class InvoiceRestController {
    @Autowired
    InvoiceService invoicingService;

    @Autowired
    ProcurementService procurementService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<InvoiceDTO>> findAllInvocies() {
        return invoicingService.findAllInvocies();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<InvoiceDTO> findOneinvoice(@PathVariable("id") String id) {
        return invoicingService.findOneInvoice(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void submitInvoice(@RequestBody Resource<InvoiceDTO> invoiceDTO) {
        System.out.println(invoiceDTO);
        invoicingService.processInvoice(invoiceDTO);
    }

    @PostMapping("/{id}/approve")
    @ResponseStatus(HttpStatus.OK)
    public Resource<InvoiceDTO> approveInvoice(@PathVariable("id") String id) {
        return invoicingService.approveInvoice(id);
    }


    @GetMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public PurchaseOrderSupplierDTO findInvoicePO(@PathVariable("id") String id) {
        Resource<InvoiceDTO> invoiceDTO = invoicingService.findOneInvoice(id);
        if(invoiceDTO == null) return null;
        return procurementService.findOnePurchaseOrder(invoiceDTO.getContent().getPlantHireRequest().getPurchaseOrder().getId());
    }


}
