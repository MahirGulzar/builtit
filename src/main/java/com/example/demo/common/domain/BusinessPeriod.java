package com.example.demo.common.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Value
@NoArgsConstructor(force=true,access= AccessLevel.PROTECTED)       // Changed from PRIVATE TO PROTECTED
@AllArgsConstructor(staticName="of")
public class BusinessPeriod {
    LocalDate startDate;
    LocalDate endDate;
}