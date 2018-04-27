package com.example.demo.procurement.domain.model;


import com.example.demo.common.domain.BusinessPeriod;
import com.example.demo.procurement.domain.model.embedable.Comment;
import com.example.demo.procurement.domain.model.enums.PHRStatus;
import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;


@Data
@Entity
public class PlantHireRequest {
    @Id
    @GeneratedValue
    long id;

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



}
