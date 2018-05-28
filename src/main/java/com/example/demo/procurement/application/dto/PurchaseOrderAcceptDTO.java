package com.example.demo.procurement.application.dto;


import com.example.demo.common.application.dto.BusinessPeriodDTO;
import com.example.demo.procurement.domain.model.Customer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;


@Data
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@AllArgsConstructor(staticName = "of")
public class PurchaseOrderAcceptDTO implements Serializable {

    public Plant plant;
    public Customer customer;
    public BusinessPeriodDTO rentalPeriod;
    public LocalDate endDate;
    public String acceptHref;
    public String rejectHref;

}
