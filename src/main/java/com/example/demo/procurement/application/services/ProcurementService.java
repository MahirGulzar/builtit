package com.example.demo.procurement.application.services;


import com.example.demo.procurement.domain.model.PurchaseOrder;
import com.example.demo.procurement.domain.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    ConstructionSiteRepository constructionSiteRepository;





}
