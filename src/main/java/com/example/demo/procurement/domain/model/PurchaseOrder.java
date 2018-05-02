package com.example.demo.procurement.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Getter
@NoArgsConstructor(force=true,access= AccessLevel.PUBLIC)
@AllArgsConstructor(staticName = "of")
public class PurchaseOrder {
    @Id
    @GeneratedValue
    Long id;
    String href;
}
