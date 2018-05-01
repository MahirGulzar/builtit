package com.example.demo.procurement.application.dto;


import lombok.Data;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
@Relation(value = "order", collectionRelation = "orders")
public class PurchaseOrderDTO extends ResourceSupport {
    Long _id;
    String href;

//    Resource<Plant> plant;
//    BusinessPeriodDTO rentalPeriod;
//
//    @Column(precision = 8, scale = 2)
//    BigDecimal total;
//    POStatus status;
}