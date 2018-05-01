package com.example.demo.procurement.application.services;


import com.example.demo.common.domain.BusinessPeriod;
import com.example.demo.procurement.application.dto.PlantHireRequest.PlantHireRequestDTO;
import com.example.demo.procurement.domain.model.ConstructionSite;
import com.example.demo.procurement.domain.model.Employee;
import com.example.demo.procurement.domain.model.PlantHireRequest;
import com.example.demo.procurement.domain.model.PlantInventoryEntry;
import com.example.demo.procurement.domain.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;


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

    public Resource<PlantHireRequestDTO> createPlantHireRequest(PlantHireRequestDTO phrDTO) {

        Employee siteEngineer = employeeRepository.getOne(phrDTO.getSiteEngineer().get_id());
        Employee worksEngineer = null;
        ConstructionSite constructionSite = constructionSiteRepository.getOne(phrDTO.getConstructionSite().get_id());

        PlantInventoryEntry plant = null;
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
        return plantHireRequestAssembler.toResource(phr);
    }




}
