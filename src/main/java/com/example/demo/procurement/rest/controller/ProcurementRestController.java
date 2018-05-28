package com.example.demo.procurement.rest.controller;

import com.example.demo.common.application.dto.BusinessPeriodDTO;
import com.example.demo.procurement.application.dto.ExtendDTO;
import com.example.demo.procurement.application.dto.PlantHireRequest.PlantHireRequestDTO;
import com.example.demo.procurement.application.dto.PurchaseOrderDTO;
import com.example.demo.procurement.application.services.ProcurementService;
import com.example.demo.procurement.domain.model.PlantHireRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/procurements/requests")
@CrossOrigin
public class ProcurementRestController {

    @Autowired
    ProcurementService procurementService;

    @GetMapping()
    public Resources<Resource<PlantHireRequestDTO>> getPlantHireRequests() {
        return procurementService.getAllPlantHireRequests();
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Resource<PlantHireRequestDTO> createPlantHireRequest(@RequestBody PlantHireRequestDTO phrDTO) {
        return procurementService.createPlantHireRequest(phrDTO);
    }

    /*
        Update Plant Hire Request
     */

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{id}")  // TODO Added becasue Front End Guy was not able to send DTO via PUT
    public Resource<PlantHireRequestDTO> updatePlantHireRequestPost(@PathVariable("id") Long id,
                                                                @RequestBody PlantHireRequestDTO phrDTO) {
        return procurementService.updatePlantHireRequest(phrDTO, id);
    }


    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public Resource<PlantHireRequestDTO> updatePlantHireRequest(@PathVariable("id") Long id,
                                                                @RequestBody PlantHireRequestDTO phrDTO) {
        return procurementService.updatePlantHireRequest(phrDTO, id);
    }


    @GetMapping("/{id}")
    public Resource<PlantHireRequestDTO> getPlantHireRequestsById(@PathVariable("id") Long id ) {
        return procurementService.getPlantHireRequestById(id);

    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}/cancel")
    public Resource<PlantHireRequestDTO> cancelPlantHireRequest(@PathVariable("id") Long id)  {
        return  procurementService.cancelPlantHireRequest(id);
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

    @PostMapping("/{id}/extend")
    public Resource<PlantHireRequestDTO> extendPlantHireRequest( @PathVariable("id") Long id, @RequestBody ExtendDTO extendDTO) throws Exception {

        if(extendDTO == null || extendDTO.getEndDate() == null) return null;

        Resource<PlantHireRequestDTO> plantHireRequestDTO = procurementService.extendPlantHireRequest(id, extendDTO.getEndDate());
        return plantHireRequestDTO;

    }

    // PO accept by RentIT
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{id}/acceptorder")
    public PurchaseOrderDTO acceptPO(@PathVariable("id") Long id) {

        System.out.println("Request for accept received with ID ="+id);
        return procurementService.acceptPO(id);
    }

    // PO reject by RentIT
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}/rejectorder")
    public PurchaseOrderDTO rejectPO(@PathVariable("id") Long id) {

        System.out.println("Request for accept received with ID ="+id);
        return procurementService.rejectPO(id);
    }



}


