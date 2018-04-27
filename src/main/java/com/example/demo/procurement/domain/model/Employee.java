package com.example.demo.procurement.domain.model;

import com.example.demo.procurement.domain.model.enums.EmployeeType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Employee {
    @Id
    @GeneratedValue
    long id;

    String name;
    @Enumerated(EnumType.STRING)
    EmployeeType employeeType;
}
