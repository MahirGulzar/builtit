package com.example.demo.procurement.integration.transformers;

import com.example.demo.procurement.application.dto.Plant;
import com.example.demo.procurement.application.dto.PlantInventoryEntryDTO;
import com.example.demo.procurement.application.dto.PurchaseOrderDTO;
import com.example.demo.procurement.application.dto.PurchaseOrderSupplierDTO;
import com.example.demo.procurement.domain.model.PlantInventoryEntry;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RentalTransformer {
    @Autowired
    @Qualifier("objectMapper")
    ObjectMapper mapper;

    public Resource<PurchaseOrderDTO> purchaseOrder(String json) {
        try {
            return mapper.readValue(json, new TypeReference<Resource<PurchaseOrderDTO>>() {});
        } catch (IOException e) {
            return null;
        }

    }

    public Resource<PurchaseOrderSupplierDTO> purchaseOrderSupplier(String json) {
        try {
            return mapper.readValue(json, new TypeReference<Resource<PurchaseOrderSupplierDTO>>() {});
        } catch (IOException e) {
            return null;
        }

    }
    public Resource<PlantInventoryEntryDTO> plantInventoryEntryTransformer(String json) {
        try {
            return mapper.readValue(json, new TypeReference<Resource<PlantInventoryEntryDTO>>() {});
        } catch (IOException e) {
            return null;
        }

    }




}