package com.example.demo.procurement.domain.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@Getter
@NoArgsConstructor(force=true,access= AccessLevel.PROTECTED)
public class PurchaseOrder {
    @Id
    String href;
}
