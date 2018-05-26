package com.example.demo.invoice.application.services;

import com.example.demo.invoice.application.dto.InvoiceDTO;
import com.example.demo.invoice.domain.model.Invoice;
import com.example.demo.invoice.rest.controller.InvoiceRestController;
import com.example.demo.procurement.rest.controller.ProcurementRestController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.afford;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Service
public class InvoiceAssembler {

    public Resources<Resource<InvoiceDTO>> toResources(List<Invoice> invoice){
        return new Resources<>(invoice.stream().map(o -> toResource(o)).collect(Collectors.toList()),
                linkTo(methodOn(InvoiceRestController.class).findAllInvocies()).withSelfRel()
                        .andAffordance(afford(methodOn(ProcurementRestController.class).createPlantHireRequest(null)))

        );
    }

    public Resource<InvoiceDTO> toResource(Invoice invoice) {

        InvoiceDTO dto = new InvoiceDTO();
        dto.set_id(invoice.getId());
        dto.setPoID(invoice.getPurchaseID());
        dto.setAmount(invoice.getAmount());
        dto.setPlantHireRequest(invoice.getPlantHireRequest());
        dto.setStatus(invoice.getStatus());
        dto.setDueDate(dto.getDueDate());

        return new Resource<>(
                dto,
                linkFor(invoice)
        );

    }

    private List<Link> linkFor(Invoice invoice) {

        switch (invoice.getStatus()) {
            case UNPAID:
                return Arrays.asList(
                        linkTo(methodOn(InvoiceRestController.class).findOneinvoice(invoice.getId())).withSelfRel()
                );
            default:
                break;
        }

        return Collections.emptyList();
    }

}
