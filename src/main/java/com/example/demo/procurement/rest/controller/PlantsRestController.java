package com.example.demo.procurement.rest.controller;

import com.example.demo.procurement.integration.gateways.RentalGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/procurements/plants")
@CrossOrigin
public class PlantsRestController {

    @Autowired
    RentalGateway rentalGateway;

    @GetMapping()
    public Object findAvailablePlants(
            @RequestParam(name = "name", required = false) String plantName,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {



//        if (plantName != null && startDate != null && endDate != null) {
//            if (endDate.isBefore(startDate)) {
//                throw new IllegalArgumentException("Something wrong with the requested period ('endDate' happens before 'startDate')");
//            }

            return rentalGateway.findPlants(plantName, startDate, endDate);
//        } else {
//            throw new IllegalArgumentException(
//                    String.format("Wrong number of parameters: Name='%s', Start date='%s', End date='%s'",
//                            plantName, startDate, endDate));
//        }
    }
}
