package com.example.demo.procurement.application.dto;


import com.example.demo.common.application.dto.BusinessPeriodDTO;
import com.example.demo.procurement.domain.model.Customer;
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
    public Customer customer;
    public BusinessPeriodDTO rentalPeriod;

    public String acceptHref;
    public String rejectHref;


}
//
//{
//        "customer":
//        {
//        "name": "BuildIt",
//        "contactPerson": "Chibaba",
//        "email": "team2@gmail.com",
//        "siteAddress": "Juhan Liivi 2"
//        },
//        "plant":
//        {
//        "_id": 1
//        },
//        "rentalPeriod":
//        {
//        "startDate": "2018-06-01",
//        "endDate": "2018-06-05"
//        }
//        }