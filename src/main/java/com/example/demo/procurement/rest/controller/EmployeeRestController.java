package com.example.demo.procurement.rest.controller;


import com.example.demo.procurement.application.dto.EmployeeDTO;
import com.example.demo.procurement.application.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin
public class EmployeeRestController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping()
    public List<EmployeeDTO> findAllEmployees() {
        return employeeService.findAllEmployees();
    }
}
