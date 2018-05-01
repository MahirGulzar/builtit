package com.example.demo.procurement.domain.model;


import com.example.demo.common.domain.BusinessPeriod;
import com.example.demo.procurement.domain.model.embedable.Comment;
import com.example.demo.procurement.domain.model.enums.PHRStatus;
import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;



@Data
@Entity
public class PlantHireRequest {
    @Id
    @GeneratedValue
    Long id;


    public static PlantHireRequest of(PlantInventoryEntry plant, BusinessPeriod rentalPeriod, Employee siteEngineer, ConstructionSite constructionSite) {
        PlantHireRequest plantHireRequest = new PlantHireRequest();
        plantHireRequest.plantInventoryEntry = plant;
        plantHireRequest.rentalPeriod = rentalPeriod;
        plantHireRequest.siteEngineer=siteEngineer;
        plantHireRequest.constructionSite=constructionSite;
        plantHireRequest.status = PHRStatus.PENDING_APPROVAL;
        plantHireRequest.totalPrice = BigDecimal.valueOf(ChronoUnit.DAYS.between(rentalPeriod.getStartDate(), rentalPeriod.getEndDate()) + 1).multiply(plant.getPrice());
        return plantHireRequest;
    }


    @OneToOne
    ConstructionSite constructionSite;

    @Embedded
    BusinessPeriod rentalPeriod;

    BigDecimal totalPrice;

    @OneToOne
    PlantInventoryEntry plantInventoryEntry;


    @OneToOne
    Employee worksEngineer;
    @OneToOne
    Employee siteEngineer;

    @Enumerated(EnumType.STRING)
    PHRStatus status;

    @Embedded
    Comment comments;

    public void ApprovePHR()
    {

    }

    



}
