package com.example.demo.procurement.application.dto.PlantHireRequest;


import com.example.demo.common.application.dto.BusinessPeriodDTO;
import com.example.demo.procurement.application.dto.*;
import com.example.demo.procurement.domain.model.PlantInventoryEntry;
import com.example.demo.procurement.domain.model.embedable.Comment;
import com.example.demo.procurement.domain.model.enums.PHRStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import java.math.BigDecimal;

@Data
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@AllArgsConstructor(staticName = "of")
public class PlantHireRequestDTO extends ResourceSupport {
    Long _id;

    Resource<EmployeeDTO> siteEngineer;

    Resource<EmployeeDTO> worksEngineer;

    Resource<ConstructionSiteDTO> constructionSite;

    PlantInventoryEntryDTO plantInventoryEntry;

    BigDecimal totalPrice;

    BusinessPeriodDTO rentalPeriod;

    PHRStatus status;

    Resource<PurchaseOrderDTO> order;

    String comments;


}