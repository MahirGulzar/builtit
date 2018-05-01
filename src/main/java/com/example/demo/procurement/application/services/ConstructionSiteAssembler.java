package com.example.demo.procurement.application.services;

import com.example.demo.procurement.application.dto.ConstructionSiteDTO;
import com.example.demo.procurement.domain.model.ConstructionSite;
import com.example.demo.procurement.rest.controller.ConstructionSiteRestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;


@Service
public class ConstructionSiteAssembler extends ResourceAssemblerSupport<ConstructionSite, ConstructionSiteDTO> {

    public ConstructionSiteAssembler() {
        super(ConstructionSiteRestController.class, ConstructionSiteDTO.class);
    }

    @Override
    public ConstructionSiteDTO toResource(ConstructionSite constructionSite) {
        if (constructionSite == null) {
            return null;
        }
        ConstructionSiteDTO dto = this.createResourceWithId(constructionSite.getId(), constructionSite);
        dto.set_id(constructionSite.getId());
        dto.setName(constructionSite.getName());
        return dto;
    }
}
