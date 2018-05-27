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
import org.springframework.web.bind.annotation.PathVariable;

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

    @Autowired
    RentalService rentalService;


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


        if(plantHireRequest.getPlantInventoryEntry() == null) {
            dto.setPlantInventoryEntry(PlantInventoryEntryDTO.of(
                    null,
                    "",
                    "",
                    null,
                    ""
                    ));

        }
        else {

            PlantInventoryEntryDTO plantInventoryEntryDTO = rentalService.getPlant(plantHireRequest.getPlantInventoryEntry().getHref());
            dto.setPlantInventoryEntry(plantInventoryEntryDTO);
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


        return new Resource<>(
                dto,
                linkFor(plantHireRequest)

        );

    }



    private List<Link> linkFor(PlantHireRequest plantHireRequest) {
        PlantHireRequestDTO plantHireRequestDTO = new PlantHireRequestDTO();

        switch (plantHireRequest.getStatus()) {
            case PENDING_APPROVAL:
                return Arrays.asList(
                        linkTo(methodOn(ProcurementRestController.class).getPlantHireRequestsById(plantHireRequest.getId())).withSelfRel(),
                        new ExtendedLink(linkTo(methodOn(ProcurementRestController.class).updatePlantHireRequest(plantHireRequest.getId(),null)).toString(), "Modify", HttpMethod.POST),
                        new ExtendedLink(linkTo(methodOn(ProcurementRestController.class).cancelPlantHireRequest(plantHireRequest.getId())).toString(), "Cancel", HttpMethod.DELETE),
                        new ExtendedLink(linkTo(methodOn(ProcurementRestController.class).approvePlantHireRequest(plantHireRequest.getId(),null)).toString(), "Accept", HttpMethod.POST),
                        new ExtendedLink(linkTo(methodOn(ProcurementRestController.class).rejectPlantHireRequest(plantHireRequest.getId(),null)).toString(), "Reject", HttpMethod.DELETE)
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
