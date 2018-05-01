package com.example.demo.procurement.application.services;

import com.example.demo.procurement.application.dto.ConstructionSiteDTO;
import com.example.demo.procurement.domain.repository.ConstructionSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ConstructionSiteService {
    @Autowired
    ConstructionSiteRepository constructionSiteRepository;

    @Autowired
    ConstructionSiteAssembler constructionSiteAssembler;

    public List<ConstructionSiteDTO> findAllProductionSites() {
        return constructionSiteAssembler.toResources(constructionSiteRepository.findAll());
    }
}
