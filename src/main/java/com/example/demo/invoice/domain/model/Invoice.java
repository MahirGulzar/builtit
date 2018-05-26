package com.example.demo.invoice.domain.model;

import com.example.demo.invoice.application.dto.InvoiceDTO;
//import com.example.demo.invoice.infrastructure.InvoiceIdentifierFactory;
import com.example.demo.procurement.domain.model.PlantHireRequest;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
public class Invoice {
    @Id
    String id;
    String purchaseID;

    @Enumerated(EnumType.STRING)
    InvoiceStatus status;

    LocalDate dueDate;
    BigDecimal amount;
    String href;

    //@Autowired
    //InvoiceIdentifierFactory factory;

    @OneToOne
    PlantHireRequest plantHireRequest;

    public void setInitialInvoice(Resource<InvoiceDTO> invoiceDTO, PlantHireRequest plantHireRequest) {
        this.setId(UUID.randomUUID().toString());
        this.setStatus(InvoiceStatus.UNPAID);
        this.setPlantHireRequest(plantHireRequest);
        this.setAmount(invoiceDTO.getContent().getAmount());
        this.setDueDate(invoiceDTO.getContent().getDueDate());
        if(invoiceDTO.hasLink("self")) this.setHref(invoiceDTO.getLink("self").get().getHref());
        else this.setHref(null);
    }

    public void approveInvoice() {
        this.setStatus(InvoiceStatus.PAID);
    }
}