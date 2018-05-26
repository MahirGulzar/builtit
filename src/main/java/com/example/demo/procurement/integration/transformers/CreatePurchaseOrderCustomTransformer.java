package com.example.demo.procurement.integration.transformers;

import com.example.demo.procurement.application.dto.Plant;
import com.example.demo.procurement.application.dto.PurchaseOrderDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import java.io.IOException;
//@Todo for project we will use this, right now we are going to REST TEMPLATE 1

@Service
public class CreatePurchaseOrderCustomTransformer {
    @Autowired
    @Qualifier("objectMapper")
    ObjectMapper mapper;

    public Resource<PurchaseOrderDTO> fromHALForms(String json) {
        try {
            return mapper.readValue(json, new TypeReference<Resource<PurchaseOrderDTO>>() {});
        } catch (IOException e) {
            return null;
        }

    }

}