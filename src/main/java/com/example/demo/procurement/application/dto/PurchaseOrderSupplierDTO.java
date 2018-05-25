package com.example.demo.procurement.application.dto;


import com.example.demo.common.domain.BusinessPeriod;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@Data

@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@AllArgsConstructor(staticName = "of")
public class PurchaseOrderSupplierDTO extends ResourceSupport {
    Long _id;
    String href;
    BusinessPeriod rentalPeriod;
    Double total;
    String  status;
}


