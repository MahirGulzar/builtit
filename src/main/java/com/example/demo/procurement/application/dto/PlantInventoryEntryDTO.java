package com.example.demo.procurement.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")

public class PlantInventoryEntryDTO {
    Long _id;
    String name;
    String description;
    BigDecimal price;
    String _link;
    String supplier;
}
