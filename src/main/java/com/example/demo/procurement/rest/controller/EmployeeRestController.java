package com.example.demo.procurement.rest.controller;


import com.example.demo.procurement.application.dto.EmployeeDTO;
import com.example.demo.procurement.application.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/procurements/employees")
@CrossOrigin
public class EmployeeRestController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping()
    public Resources<?> findAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @GetMapping("/{id}")
    public Resource<EmployeeDTO> findEmployee(@PathVariable("id") Long id) {
        return employeeService.findEmployee(id);
    }
}
