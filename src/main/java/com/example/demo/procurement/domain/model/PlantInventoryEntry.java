package com.example.demo.procurement.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
public class PlantInventoryEntry {
    @Id
    @GeneratedValue
    Long _id;
    String href;
//    String name;
//    String description;
//    BigDecimal price;
//    String _link;
//    String supplier;

}
