package com.example.demo.procurement.application.dto;

import com.example.demo.procurement.domain.model.enums.EmployeeType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
public class EmployeeDTO extends ResourceSupport {
    Long _id;
    String name;
    @Enumerated(EnumType.STRING)
    EmployeeType type;
}
