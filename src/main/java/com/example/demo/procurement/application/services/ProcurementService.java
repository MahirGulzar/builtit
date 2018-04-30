package com.example.demo.procurement.application.services;


import com.example.demo.procurement.application.dto.PlantInventoryEntryDTO;
import com.example.demo.procurement.domain.model.PurchaseOrder;
import com.example.demo.procurement.domain.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class ProcurementService {

/*    @Autowired
    RestTemplate restTemplate;*/

    @Autowired
    PlantHireRequestRepository plantHireRequestRepository;

    @Autowired
    PlantInventoryEntryRepository plantInventoryEntryRepository;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ConstructionSiteRepository constructionSiteRepository;

/*
    public Resources<?> findAvailablePlants(String plantName, LocalDate startDate, LocalDate endDate) {
        PlantInventoryEntryDTO[] plants = restTemplate.getForObject(
                "http://localhost:8090/api/inventory/plants?name={name}&startDate={start}&endDate={end}",
                PlantInventoryEntryDTO[].class, plantName, startDate, endDate);
        return plants;
    }
*/



}
