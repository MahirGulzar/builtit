package com.example.demo.procurement.rest.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/procurements/requests")
@CrossOrigin
public class ProcurementRestController {
    //service.findPlants("exc", LocalDate.now(), LocalDate.now().plusDays(2))
}
