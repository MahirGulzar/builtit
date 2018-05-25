package com.example.demo.procurement.application.dto;


import com.example.demo.procurement.domain.model.PurchaseOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
@Relation(value = "order", collectionRelation = "orders")
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@AllArgsConstructor(staticName = "of")
public class PurchaseOrderDTO extends ResourceSupport {
    Long _id;
    String href;
    public PurchaseOrder asPurchaseOrder(){
        return PurchaseOrder.of(_id, href);
    }
}