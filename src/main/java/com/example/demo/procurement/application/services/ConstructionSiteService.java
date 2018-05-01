package com.example.demo.procurement.application.services;

import com.example.demo.procurement.application.dto.ConstructionSiteDTO;
import com.example.demo.procurement.domain.repository.ConstructionSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ConstructionSiteService {
    @Autowired
    ConstructionSiteRepository constructionSiteRepository;

    @Autowired
    ConstructionSiteAssembler constructionSiteAssembler;




    public Resources<?> findAllProductionSites() {
        return constructionSiteAssembler.toResources(constructionSiteRepository.findAll());
    }

    public Resource<ConstructionSiteDTO> findConstructionSite(long id) {
        return constructionSiteAssembler.toResource(constructionSiteRepository.getOne(id));
    }
}
