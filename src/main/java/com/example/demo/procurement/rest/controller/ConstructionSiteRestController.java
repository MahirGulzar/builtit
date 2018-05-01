package com.example.demo.procurement.rest.controller;


import com.example.demo.procurement.application.dto.ConstructionSiteDTO;
import com.example.demo.procurement.application.services.ConstructionSiteService;
import com.example.demo.procurement.domain.model.ConstructionSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/sites/constructionsites")
@CrossOrigin
public class ConstructionSiteRestController {

    @Autowired
    ConstructionSiteService constructionSiteService;

    @GetMapping()
    public List<ConstructionSiteDTO> findAllProductionSites() {
        return constructionSiteService.findAllProductionSites();
    }
}
