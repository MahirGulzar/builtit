package com.example.demo.procurement.integration.transformers;

import com.example.demo.procurement.application.dto.Plant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindPlantsCustomTransformer {
    @Autowired
    @Qualifier("objectMapper")
    ObjectMapper mapper;

    public Resources<Resource<Plant>> fromJson(String json) {
        try {
            List<Plant> plants = mapper.readValue(json, new TypeReference<List<Plant>>() {});
            return new Resources<>(plants.stream().map(p -> new Resource<>(p, new Link("http://localhost:8088/api/v1/plant/" + p.get_id()))).collect(Collectors.toList()));
        } catch (IOException e) {
            return null;
        }
    }

    public Resources<Resource<Plant>> fromHALForms(String json) {
        try {
            return mapper.readValue(json, new TypeReference<Resources<Resource<Plant>>>() {});
        } catch (IOException e) {
            return null;
        }
    }

}