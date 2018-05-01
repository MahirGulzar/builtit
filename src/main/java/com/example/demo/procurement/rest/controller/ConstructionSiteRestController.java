package com.example.demo.procurement.rest.controller;


import com.example.demo.procurement.application.dto.ConstructionSiteDTO;
import com.example.demo.procurement.application.services.ConstructionSiteService;
import com.example.demo.procurement.domain.model.ConstructionSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/procurements/constructionsites")
@CrossOrigin
public class ConstructionSiteRestController {

    @Autowired
    ConstructionSiteService constructionSiteService;

    @GetMapping()
    public Resources<?> findAllConstructionSites() {
        return constructionSiteService.findAllProductionSites();
    }
    @GetMapping("/{id}")
    public Resource<ConstructionSiteDTO> findConstructionSite(@PathVariable("id") Long id) {
        return constructionSiteService.findConstructionSite(id);
    }
}
