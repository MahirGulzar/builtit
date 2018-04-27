package com.example.demo.procurement.domain.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(force=true,access= AccessLevel.PROTECTED)
public class PlantInventoryEntry {
    @Id
    String href;

    String name;
}
