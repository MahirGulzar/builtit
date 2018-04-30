package com.example.demo.procurement.application.services;


import com.example.demo.procurement.domain.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Plant[] plants = restTemplate.getForObject(
                "http://localhost:8090/api/inventory/plants?name={name}&startDate={start}&endDate={end}",
                Plant[].class, plantName, startDate, endDate);
        return plants;
    }
*/



}
