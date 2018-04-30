package com.example.demo.rental.integration.transformers;

import com.example.demo.procurement.application.dto.PlantInventoryEntryDTO;
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
public class CustomTransformer {
    @Autowired
    ObjectMapper mapper;

    public Resources<Resource<PlantInventoryEntryDTO>> fromJson(String json) {
        try {
            List<PlantInventoryEntryDTO> plants = mapper.readValue(json, new TypeReference<List<PlantInventoryEntryDTO>>() {});
            return new Resources<>(plants.stream().map(p -> new Resource<>(p, new Link("http://localhost:8088/api/v1/plant/" + p.get_id()))).collect(Collectors.toList()));
        } catch (IOException e) {
            return null;
        }
    }

    public Resources<Resource<PlantInventoryEntryDTO>> fromHALForms(String json) {
        try {
            return mapper.readValue(json, new TypeReference<Resources<Resource<PlantInventoryEntryDTO>>>() {});
        } catch (IOException e) {
            return null;
        }
    }

}