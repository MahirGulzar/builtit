package com.example.demo.procurement.application.dto;

import com.example.demo.common.application.dto.BusinessPeriodDTO;
import lombok.Data;

@Data
public class CatalogQueryDTO {
    String name;
    BusinessPeriodDTO rentalPeriod;
}
