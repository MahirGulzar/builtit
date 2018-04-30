package com.example.demo.procurement.domain.model;


import com.example.demo.common.domain.BusinessPeriod;
import com.example.demo.procurement.domain.model.embedable.Comment;
import com.example.demo.procurement.domain.model.enums.PHRStatus;
import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


@Data
@Entity
public class PlantHireRequest {
    @Id
    @GeneratedValue
    long id;


    public static PlantHireRequest of(PlantInventoryEntry plant, BusinessPeriod rentalPeriod, Employee siteEngineer, ConstructionSite constructionSite) {
        PlantHireRequest plantHireRequest = new PlantHireRequest();
        plantHireRequest.plantInventoryEntry = plant;
        plantHireRequest.rentalPeriod = rentalPeriod;
        plantHireRequest.siteEngineer=siteEngineer;
        plantHireRequest.status = PHRStatus.PENDING_APPROVAL;
//        plantHireRequest.totalPrice = BigDecimal.valueOf(ChronoUnit.DAYS.between(rentalPeriod.getStartDate(), rentalPeriod.getEndDate()) + 1).multiply(plant.getPrice());
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

    @OneToOne
    PurchaseOrder purchaseOrder;

    @Embedded
    Comment comments;

    public void approvePHR(Employee worksEngineer, PurchaseOrder purchaseOrder) {
        this.worksEngineer=worksEngineer;
        this.status=PHRStatus.APPROVED;
        this.purchaseOrder=purchaseOrder;
    }
    public void rejectPHR(Employee worksEngineer) {
        this.worksEngineer=worksEngineer;
        this.status=PHRStatus.REJECTED;
    }
    public void addComments(String comments) {
        this.comments = Comment.of(comments);
    }






}
