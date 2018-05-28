package com.example.demo.invoice.application.services;

import com.example.demo.invoice.application.dto.InvoiceDTO;
import com.example.demo.invoice.application.dto.RemittanceDTO;
import com.example.demo.invoice.domain.model.Invoice;
import com.example.demo.invoice.domain.model.InvoiceStatus;
import com.example.demo.invoice.domain.repository.InvoiceRepository;
import com.example.demo.procurement.domain.model.PlantHireRequest;
import com.example.demo.procurement.domain.model.enums.POStatus;
import com.example.demo.procurement.domain.repository.PlantHireRequestRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    InvoiceAssembler invoiceAssembler;

    @Autowired
    PlantHireRequestRepository plantHireRequestRepository;

    @Autowired
    RemittanceService remittanceService;


    @Autowired
    @Qualifier("objectMapper")
    ObjectMapper mapper;

    public Resources<Resource<InvoiceDTO>> findAllInvocies() {
        return invoiceAssembler.toResources(invoiceRepository.findAll());
    }

    public Resource<InvoiceDTO> findOneInvoice(String id) {
        Invoice invoice = invoiceRepository.getOne(id);
        if(invoice == null) return null;
        return invoiceAssembler.toResource(invoice);
    }

    public Resource<InvoiceDTO> approveInvoice(String id) {
        Invoice invoice = invoiceRepository.getOne(id);

        if(invoice == null) return null;

        invoice.approveInvoice();
        invoice.getPlantHireRequest().getPurchaseOrder().setPoStatus(POStatus.PAID);

        RemittanceDTO remittanceDTO = RemittanceDTO.of(
                invoice.getPlantHireRequest().getPurchaseOrder().getHref(),
                invoice.getAmount(),
                LocalDate.now()
        );



        remittanceService.sendRemittanceHTTP(remittanceDTO);

        invoiceRepository.save(invoice);

        return invoiceAssembler.toResource(invoice);

    }

    public Boolean processInvoice(Resource<InvoiceDTO> invoiceDTO) {
        System.out.println(invoiceDTO);
        if(invoiceDTO == null || invoiceDTO.getContent().getPoID() == null) {
            System.out.println("no invoice");
            return false;
        };

        PlantHireRequest plantHireRequest = plantHireRequestRepository.findPHRbyRef(invoiceDTO.getContent().getPoID());
        System.out.println(plantHireRequest);
        if(plantHireRequest != null && plantHireRequest.getPurchaseOrder().getPoStatus() == POStatus.UNPAID){
            Invoice invoice = new Invoice();
            invoice.setInitialInvoice(invoiceDTO, plantHireRequest);
            invoiceRepository.save(invoice);
            System.out.println(invoice);
            return true;
        }


        return false;
    }

    public Resource<InvoiceDTO> stringToResource(String invoiceString) {
        System.out.println('a');
        Resource<InvoiceDTO> invoiceDTO = null;
        System.out.println('b');
        try {
            invoiceDTO = mapper.readValue(invoiceString, new TypeReference<Resource<InvoiceDTO>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invoiceDTO;
    }
}