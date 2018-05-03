package com.example.demo.procurement.application.services;


import com.example.demo.common.domain.BusinessPeriod;
import com.example.demo.procurement.application.dto.Plant;
import com.example.demo.procurement.application.dto.PlantHireRequest.PlantHireRequestDTO;
import com.example.demo.procurement.application.dto.PurchaseOrderAcceptDTO;
import com.example.demo.procurement.application.dto.PurchaseOrderDTO;
import com.example.demo.procurement.domain.model.*;
import com.example.demo.procurement.domain.model.embedable.Comment;
import com.example.demo.procurement.domain.repository.*;
import com.example.demo.procurement.rest.controller.EmployeeRestController;
import com.example.demo.procurement.rest.controller.ProcurementRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProcurementService {


    @Autowired
    PlantHireRequestRepository plantHireRequestRepository;

    @Autowired
    PlantInventoryEntryRepository plantInventoryEntryRepository;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PlantHireRequestAssembler plantHireRequestAssembler;


    @Autowired
    ConstructionSiteRepository constructionSiteRepository;

    @Autowired
    RentalService rentalService;


    public Resource<PlantHireRequestDTO> approvePlantHireRequest(PlantHireRequestDTO plantHireRequestDTO){

        Employee worksEngineer = employeeRepository.getOne(plantHireRequestDTO.getWorksEngineer().getContent().get_id());
        if(worksEngineer==null)
        {
            //TODO throw exception or something here...
        }

        PlantHireRequest plantHireRequest = plantHireRequestRepository.getOne(plantHireRequestDTO.get_id());


        PurchaseOrderAcceptDTO po = PurchaseOrderAcceptDTO.of(
                Plant.of(
                        plantHireRequest.getPlantInventoryEntry().get_id(),
                        null,
                        null,
                        null
                ),
                plantHireRequestDTO.getRentalPeriod(),
                linkTo(methodOn(ProcurementRestController.class).acceptPO(plantHireRequest.getId())).toString(),
                linkTo(methodOn(ProcurementRestController.class).rejectPO(plantHireRequest.getId())).toString()
        );

        PurchaseOrderDTO rtnPo =  rentalService.createPurchaseOrder(po);

        PurchaseOrder phrPo = new PurchaseOrder();
        phrPo.setHref(rtnPo.getHref());
        purchaseOrderRepository.save(phrPo);

        plantHireRequest.approvePHR(worksEngineer,phrPo);
        System.out.println(phrPo);
        plantHireRequest.setComments(Comment.of(plantHireRequestDTO.getComments().getComment()));
        plantHireRequestRepository.save(plantHireRequest);
        return plantHireRequestAssembler.toResource(plantHireRequest);
    }


    public Resource<PlantHireRequestDTO> updatePlantHireRequest(PlantHireRequestDTO plantHireRequestDTO){

        Employee worksEngineer = employeeRepository.getOne(plantHireRequestDTO.getWorksEngineer().getContent().get_id());
        if(worksEngineer==null)
        {
            //TODO throw exception or something here...
        }

        PlantHireRequest plantHireRequest = plantHireRequestRepository.getOne(plantHireRequestDTO.get_id());
        
        plantHireRequest.setComments(plantHireRequestDTO.getComments());
        plantHireRequest.setConstructionSite(constructionSiteRepository.getOne(plantHireRequestDTO.getConstructionSite().getContent().get_id()));
        plantHireRequest.setSiteEngineer(employeeRepository.getOne(plantHireRequestDTO.getConstructionSite().getContent().get_id()));
        plantHireRequest.setStatus(plantHireRequestDTO.getStatus());

        plantHireRequestRepository.save(plantHireRequest);
        return plantHireRequestAssembler.toResource(plantHireRequest);
    }

    public Resource<PlantHireRequestDTO> rejectPlantHireRequest(PlantHireRequestDTO plantHireRequestDTO){

        Employee worksEngineer = employeeRepository.getOne(plantHireRequestDTO.getWorksEngineer().getContent().get_id());
        if(worksEngineer==null)
        {
            //TODO throw exception or something here...
        }

        PlantHireRequest plantHireRequest = plantHireRequestRepository.getOne(plantHireRequestDTO.get_id());


        plantHireRequest.rejectPHR(worksEngineer);
        plantHireRequest.setComments(Comment.of(plantHireRequestDTO.getComments().getComment()));
        plantHireRequestRepository.save(plantHireRequest);
        return plantHireRequestAssembler.toResource(plantHireRequest);
    }

    public Resource<PlantHireRequestDTO> getPlantHireRequestById(long id)
    {
        return plantHireRequestAssembler.toResource(plantHireRequestRepository.findById(id));
    }

    public Resources<Resource<PlantHireRequestDTO>> getAllPlantHireRequests(){

        return plantHireRequestAssembler.toResources(plantHireRequestRepository.findAll());
    }

    public Resource<PlantHireRequestDTO> createPlantHireRequest(PlantHireRequestDTO phrDTO) {

        Employee siteEngineer = employeeRepository.getOne(phrDTO.getSiteEngineer().getContent().get_id());
        Employee worksEngineer = null;

        System.out.println(siteEngineer);
        ConstructionSite constructionSite = constructionSiteRepository.getOne(phrDTO.getConstructionSite().getContent().get_id());

        PlantInventoryEntry plant = null;
        System.out.println(phrDTO.getPlantInventoryEntry());
        if(phrDTO.getPlantInventoryEntry() != null) {
            plant = PlantInventoryEntry.of(
                        phrDTO.getPlantInventoryEntry().get_id(),
                        phrDTO.getPlantInventoryEntry().getName(),
                        phrDTO.getPlantInventoryEntry().getDescription(),
                        phrDTO.getPlantInventoryEntry().getPrice(),
                        phrDTO.getPlantInventoryEntry().get_link(),
                        null);
            plantInventoryEntryRepository.save(plant);
        }
        else {
            return null;
        }


        BusinessPeriod rentalPeriod = null;
        if(phrDTO.getRentalPeriod() != null) {
            rentalPeriod = BusinessPeriod.of(
                    phrDTO.getRentalPeriod().getStartDate(),
                    phrDTO.getRentalPeriod().getEndDate());
        }
        else {
            return null;
        }

        PlantHireRequest request = PlantHireRequest.of(
                plant,
                rentalPeriod,
                siteEngineer,
                constructionSite
        );

        PlantHireRequest phr = plantHireRequestRepository.save(request);
        System.out.println("--------------"+request);
        return plantHireRequestAssembler.toResource(phr);

    }


    public PurchaseOrderDTO acceptPO(Long id) {

        PlantHireRequest phr = plantHireRequestRepository.getOne(id);
        if(phr == null) return null;

        phr.acceptPO();

        plantHireRequestRepository.save(phr);

        // TODO return full representation of PO here
        PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();

        return purchaseOrderDTO;
    }

    public PurchaseOrderDTO rejectPO(Long id) {

        PlantHireRequest phr = plantHireRequestRepository.getOne(id);
        if(phr == null) return null;

        phr.rejectPO();

        plantHireRequestRepository.save(phr);

        // TODO return full representation of PO here
        PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();

        return purchaseOrderDTO;
    }

}
