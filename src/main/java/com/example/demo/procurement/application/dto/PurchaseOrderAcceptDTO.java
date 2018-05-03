package com.example.demo.procurement.application.dto;


import com.example.demo.common.application.dto.BusinessPeriodDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@AllArgsConstructor(staticName = "of")
public class PurchaseOrderAcceptDTO implements Serializable {

    public Plant plant;
    public BusinessPeriodDTO rentalPeriod;

    public String acceptHref;
    public String rejectHref;


}