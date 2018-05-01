package com.example.demo.procurement.application.services;


import com.example.demo.procurement.application.dto.ConstructionSiteDTO;
import com.example.demo.procurement.application.dto.EmployeeDTO;
import com.example.demo.procurement.domain.model.ConstructionSite;
import com.example.demo.procurement.domain.model.Employee;
import com.example.demo.procurement.domain.repository.EmployeeRepository;
import com.example.demo.procurement.rest.controller.ConstructionSiteRestController;
import com.example.demo.procurement.rest.controller.EmployeeRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class EmployeeAssembler{

    public Resources<?> toResources(List<Employee> employees){
        return new Resources<>(employees.stream().map(o -> toResource(o)).collect(Collectors.toList()),
                linkTo(methodOn(EmployeeRestController.class).findAllEmployees()).withSelfRel()
        );
    }


    public Resource<EmployeeDTO> toResource(Employee employee) {
        if (employee == null) {
            return null;
        }
        EmployeeDTO dto = new EmployeeDTO();
        dto.set_id(employee.getId());
        dto.setName(employee.getName());
        dto.setType(employee.getType());
        return new Resource<>(
                dto,
                linkTo(methodOn(EmployeeRestController.class).findEmployee(employee.getId())).withSelfRel()
        );
    }
}
