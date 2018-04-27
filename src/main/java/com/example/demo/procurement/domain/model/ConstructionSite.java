package com.example.demo.procurement.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ConstructionSite {
    @Id
    @GeneratedValue
    long id;

    String name;
}
