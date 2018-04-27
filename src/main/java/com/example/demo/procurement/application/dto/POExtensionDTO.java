package com.example.demo.procurement.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDate;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor(staticName = "of")
@Relation(value = "extension", collectionRelation = "extensions")
public class POExtensionDTO {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate endDate;

}
