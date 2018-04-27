package com.example.demo.common.application.dto;

import com.example.demo.common.domain.BusinessPeriod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor(force = true) // for serialization on HTML
@AllArgsConstructor(staticName = "of")
public class BusinessPeriodDTO {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate endDate;



    //----------------------------------------------------------------------

    @Autowired
    public BusinessPeriod asBusinessPeriod()
    {
        return BusinessPeriod.of(this.startDate,this.endDate);
    }

    //----------------------------------------------------------------------
}