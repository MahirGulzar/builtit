package com.example.demo.procurement.application.services;

import com.example.demo.procurement.application.dto.ConstructionSiteDTO;
import com.example.demo.procurement.application.dto.PlantHireRequest.PlantHireRequestDTO;
import com.example.demo.procurement.domain.model.ConstructionSite;
import com.example.demo.procurement.domain.model.PlantHireRequest;
import com.example.demo.procurement.domain.repository.ConstructionSiteRepository;
import com.example.demo.procurement.rest.controller.ConstructionSiteRestController;
import com.example.demo.procurement.rest.controller.ProcurementRestController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.afford;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@Service
public class ConstructionSiteAssembler {



    public Resources<?> toResources(List<ConstructionSite> sites){
        return new Resources<>(sites.stream().map(o -> toResource(o)).collect(Collectors.toList()),
                linkTo(methodOn(ConstructionSiteRestController.class).findAllConstructionSites()).withSelfRel()
        );
    }


    public Resource<ConstructionSiteDTO> toResource(ConstructionSite constructionSite) {
        if (constructionSite == null) {
            return null;
        }
        ConstructionSiteDTO dto = new ConstructionSiteDTO();
        dto.set_id(constructionSite.getId());
        dto.setName(constructionSite.getName());
        return new Resource<>(
                dto,
                linkTo(methodOn(ConstructionSiteRestController.class).findConstructionSite(constructionSite.getId())).withSelfRel()
        );
    }
}
