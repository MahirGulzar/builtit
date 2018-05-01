package com.example.demo.procurement.rest.controller;

import com.example.demo.procurement.application.dto.PlantHireRequest.PlantHireRequestDTO;
import com.example.demo.procurement.application.dto.PurchaseOrderDTO;
import com.example.demo.procurement.application.services.ProcurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/procurements/requests")
@CrossOrigin
public class ProcurementRestController {

    @Autowired
    ProcurementService procurementService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Resource<PlantHireRequestDTO> createPlantHireRequest(@RequestBody PlantHireRequestDTO phrDTO) {
//        System.out.println(phrDTO);
        return procurementService.createPlantHireRequest(phrDTO);

    }

    @GetMapping()
    public Resources<Resource<PlantHireRequestDTO>> getPlantHireRequests() {
//        System.out.println(phrDTO);
        return procurementService.getAllPlantHireRequests();

    }

    @GetMapping("/{id}")
    public Resource<PlantHireRequestDTO> getPlantHireRequestsById(@PathVariable("id") Long id ) {
        return procurementService.getPlantHireRequestById(id);

    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public Resource<PlantHireRequestDTO> updatePlantHireRequest(@PathVariable("id") Long id,
                                                                 @RequestBody PlantHireRequestDTO phrDTO) {
        return procurementService.updatePlantHireRequest(phrDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{id}/accept")
    public Resource<PlantHireRequestDTO> approvePlantHireRequest(@PathVariable("id") Long id,
                                                                 @RequestBody PlantHireRequestDTO phrDTO) {
        return procurementService.approvePlantHireRequest(phrDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}/accept")
    public Resource<PlantHireRequestDTO> rejectPlantHireRequest(@PathVariable("id") Long id,
                                                                @RequestBody PlantHireRequestDTO phrDTO) {
        return procurementService.rejectPlantHireRequest(phrDTO);
    }
}


