package com.example.demo.procurement.application.dto;



import lombok.*;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDate;

@Data
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@AllArgsConstructor(staticName = "of")
public class ExtendDTO extends ResourceSupport {
    LocalDate endDate;
}

