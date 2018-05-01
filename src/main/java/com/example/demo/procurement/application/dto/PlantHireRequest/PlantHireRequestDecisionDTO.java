package com.example.demo.procurement.application.dto.PlantHireRequest;


import com.example.demo.common.application.dto.BusinessPeriodDTO;
import com.example.demo.procurement.application.dto.ConstructionSiteDTO;
import com.example.demo.procurement.application.dto.EmployeeDTO;
import com.example.demo.procurement.application.dto.PurchaseOrderDTO;
import com.example.demo.procurement.domain.model.PlantInventoryEntry;
import com.example.demo.procurement.domain.model.embedable.Comment;
import com.example.demo.procurement.domain.model.enums.PHRStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;

@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
public class PlantHireRequestDecisionDTO extends ResourceSupport {
    String _id;
    EmployeeDTO worksEngineer;
    PHRStatus status;
    Comment comments;
}