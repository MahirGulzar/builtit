package com.example.demo.invoice.application.dto;

import com.example.demo.invoice.domain.model.InvoiceStatus;
import com.example.demo.procurement.domain.model.PlantHireRequest;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InvoiceDTO  extends ResourceSupport {
    String _id;
    String poID;
    BigDecimal amount;
    PlantHireRequest plantHireRequest;
    InvoiceStatus status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dueDate;


//    public Invoice asInvoice(){
//        return Invoice.of(_id, poID, amount, dueDate, phr, status);
//    }
}
