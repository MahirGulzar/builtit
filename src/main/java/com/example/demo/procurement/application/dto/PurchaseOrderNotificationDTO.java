package com.example.demo.procurement.application.dto;

import com.example.demo.common.application.dto.BusinessPeriodDTO;
import com.example.demo.procurement.domain.model.enums.POStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
public class PurchaseOrderNotificationDTO extends ResourceSupport {
    Long _id;
    Resource<PlantInventoryEntryDTO> plant;
    BusinessPeriodDTO rentalPeriod;

    @Column(precision = 8, scale = 2)
    BigDecimal total;
    POStatus status;

    // Added for notification on BuiltIT
    String acceptHref;
    String rejectHref;

    String consumerURI;


    @JsonCreator // Added to test basic authentication
    public PurchaseOrderNotificationDTO() {
    }
}