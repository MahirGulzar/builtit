package com.example.demo.procurement.application.services;

import com.example.demo.common.utils.ExtendedLink;
import com.example.demo.common.application.dto.BusinessPeriodDTO;
import com.example.demo.procurement.application.dto.CommentDTO;
import com.example.demo.procurement.application.dto.PlantHireRequest.PlantHireRequestDTO;
import com.example.demo.procurement.application.dto.PlantInventoryEntryDTO;
import com.example.demo.procurement.domain.model.PlantHireRequest;
import com.example.demo.procurement.rest.controller.ProcurementRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.afford;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@Service
public class PlantHireRequestAssembler {

    @Autowired
    EmployeeAssembler employeeAssembler;
    @Autowired
    ConstructionSiteAssembler productionSiteAssembler;

    @Autowired
    PurchaseOrderAssembler purchaseOrderAssembler;


    public Resources<Resource<PlantHireRequestDTO>> toResources(List<PlantHireRequest> orders){
        return new Resources<>(orders.stream().map(o -> toResource(o)).collect(Collectors.toList()),
                linkTo(methodOn(ProcurementRestController.class).getPlantHireRequests()).withSelfRel()
                        .andAffordance(afford(methodOn(ProcurementRestController.class).createPlantHireRequest(null)))

        );
    }



    public Resource<PlantHireRequestDTO> toResource(PlantHireRequest plantHireRequest) {

        PlantHireRequestDTO dto = new PlantHireRequestDTO();

        dto.set_id(plantHireRequest.getId());
        dto.setSiteEngineer(employeeAssembler.toResource(plantHireRequest.getSiteEngineer()));

        dto.setWorksEngineer(employeeAssembler.toResource(plantHireRequest.getWorksEngineer()));
        dto.setConstructionSite(productionSiteAssembler.toResource(plantHireRequest.getConstructionSite()));
        dto.setOrder(purchaseOrderAssembler.toResource(plantHireRequest.getPurchaseOrder()));

        if(plantHireRequest.getComments()!=null) {
          //  dto.setComments(plantHireRequest.getComments().getComment()); //TODO verify
        }

        System.out.println("Check 3");
        if(plantHireRequest.getPlantInventoryEntry() == null) {
            dto.setPlantInventoryEntry(PlantInventoryEntryDTO.of(
                    null,
                    "",
                    "",
                    null,
                    "",
                    ""));

        }
        else {
            dto.setPlantInventoryEntry(PlantInventoryEntryDTO.of(
                    plantHireRequest.getPlantInventoryEntry().get_id(),
                    plantHireRequest.getPlantInventoryEntry().getName(),
                    plantHireRequest.getPlantInventoryEntry().getDescription(),
                    plantHireRequest.getPlantInventoryEntry().getPrice(),
                    plantHireRequest.getPlantInventoryEntry().get_link(),
                    plantHireRequest.getPlantInventoryEntry().getSupplier()));
        }

        dto.setTotalPrice(plantHireRequest.getTotalPrice());
        if(plantHireRequest.getRentalPeriod() == null) {
            dto.setRentalPeriod(BusinessPeriodDTO.of(
                    LocalDate.now(),
                    LocalDate.now()));
        }
        else {
            dto.setRentalPeriod(BusinessPeriodDTO.of(
                    plantHireRequest.getRentalPeriod().getStartDate(),
                    plantHireRequest.getRentalPeriod().getEndDate()));
        }
        dto.setStatus(plantHireRequest.getStatus());

/*
        switch (dto.getStatus()) {
            case PENDING_APPROVAL:
                dto.add(new ExtendedLink(
                        linkTo(methodOn(ProcurementRestController.class)
                                .approvePlantHireRequest(dto.get_id(), dto)).toString(),
                        "approvePHR", POST));
                dto.add(new ExtendedLink(
                        linkTo(methodOn(ProcurementRestController.class)
                                .rejectPlantHireRequest(dto.get_id(), dto)).toString(),
                        "rejectPHR", DELETE));
                dto.add(new ExtendedLink(
                        linkTo(methodOn(ProcurementRestController.class)
                                .updatePlantHireRequest(dto.get_id(), dto)).toString(),
                        "updatePHR", PATCH));
                break;
            case APPROVED:
                dto.add(new ExtendedLink(
                        linkTo(methodOn(ProcurementRestController.class)
                                .fixPurchaseOrder(dto.get_id(), null)).toString(),
                        "fixPHR", PATCH));
                break;
            case REJECTED:
                dto.add(new ExtendedLink(
                        linkTo(methodOn(ProcurementRestController.class)
                                .updatePlantHireRequest(dto.get_id(), dto)).toString(),
                        "updatePHR", PATCH));
                break;
            default:
                break;
        }
*/

        return new Resource<>(
                dto,
                linkFor(plantHireRequest)

        );

    }



    private List<Link> linkFor(PlantHireRequest plantHireRequest) {

        switch (plantHireRequest.getStatus()) {
            case PENDING_APPROVAL:
                return Arrays.asList(
                        linkTo(methodOn(ProcurementRestController.class).getPlantHireRequestsById(plantHireRequest.getId())).withSelfRel(),
                        new ExtendedLink(linkTo(methodOn(ProcurementRestController.class).approvePlantHireRequest(plantHireRequest.getId(),null)).toString(), "accept", HttpMethod.POST),
                        new ExtendedLink(linkTo(methodOn(ProcurementRestController.class).rejectPlantHireRequest(plantHireRequest.getId(),null)).toString(), "reject", HttpMethod.DELETE)
                );
            case APPROVED:
                return Arrays.asList(
                        linkTo(methodOn(ProcurementRestController.class).getPlantHireRequestsById(plantHireRequest.getId())).withSelfRel()
                );
            case REJECTED:
                return Arrays.asList(
                        linkTo(methodOn(ProcurementRestController.class).getPlantHireRequestsById(plantHireRequest.getId())).withSelfRel()
                );
            default:
                break;
        }

        return Collections.emptyList();
    }

}
