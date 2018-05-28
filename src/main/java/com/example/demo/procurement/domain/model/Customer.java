package com.example.demo.procurement.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public final class Customer {
    @Id
    @GeneratedValue
    Long _id;
    String name = "Team 12";
    String contactPerson = "Junaid Ahmed";
    String email = "esiteam12@gmail.com";
    String siteAddress = "Team 12";
}
