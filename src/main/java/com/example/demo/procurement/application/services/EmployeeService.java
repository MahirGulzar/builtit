package com.example.demo.procurement.application.services;

import com.example.demo.procurement.application.dto.ConstructionSiteDTO;
import com.example.demo.procurement.application.dto.EmployeeDTO;
import com.example.demo.procurement.domain.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeAssembler employeeAssembler;


    public Resources<?> findAllEmployees() {
        return employeeAssembler.toResources(employeeRepository.findAll());
    }

    public Resource<EmployeeDTO> findEmployee(long id) {
        return employeeAssembler.toResource(employeeRepository.getOne(id));
    }

}
