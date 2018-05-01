package com.example.demo.procurement.application.services;


import com.example.demo.procurement.application.dto.EmployeeDTO;
import com.example.demo.procurement.domain.model.Employee;
import com.example.demo.procurement.domain.repository.EmployeeRepository;
import com.example.demo.procurement.rest.controller.EmployeeRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeAssembler extends ResourceAssemblerSupport<Employee, EmployeeDTO> {
    @Autowired
    EmployeeRepository employeeRepositoryRepository;

    public EmployeeAssembler() {
        super(EmployeeRestController.class, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO toResource(Employee employee) {
        if (employee == null) {
            return null;
        }
        EmployeeDTO dto = this.createResourceWithId(employee.getId(), employee);
        dto.set_id(employee.getId());
        dto.setName(employee.getName());
        dto.setType(employee.getType());
        return dto;
    }
}
