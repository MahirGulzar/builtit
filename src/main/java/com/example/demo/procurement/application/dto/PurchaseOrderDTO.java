package com.example.demo.procurement.application.dto;


import com.example.demo.procurement.domain.model.PurchaseOrder;
import com.example.demo.procurement.domain.model.enums.POStatus;
import gherkin.lexer.Tr;
import lombok.*;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
@Relation(value = "order", collectionRelation = "orders")
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(callSuper=false)
public class PurchaseOrderDTO extends ResourceSupport {
    Long _id;
    String href;
    POStatus poStatus;
    public PurchaseOrder asPurchaseOrder(){
        return PurchaseOrder.of(_id, href, poStatus);
    }

    public String getCancelURL() {
        String splitted[] = this.href.split("/");
        System.out.println(splitted[2]);
        String url = splitted[2];
        switch (url) {
            case "team-2-rentit.herokuapp.com":
                return this.href;
            default:
                return this.href + "/cancel";
        }
    }
}

