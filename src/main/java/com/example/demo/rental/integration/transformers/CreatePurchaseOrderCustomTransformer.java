package com.example.demo.rental.integration.transformers;

import com.example.demo.procurement.application.dto.Plant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreatePurchaseOrderCustomTransformer {
    @Autowired
    ObjectMapper mapper;

    public Resources<Resource<Plant>> fromHALForms(String json) {
        try {
            return mapper.readValue(json, new TypeReference<Resources<Resource<Plant>>>() {});
        } catch (IOException e) {
            return null;
        }
    }

}