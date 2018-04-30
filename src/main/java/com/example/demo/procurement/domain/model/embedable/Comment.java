package com.example.demo.procurement.domain.model.embedable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Embeddable;

@Data
@Embeddable
@AllArgsConstructor(staticName = "of")
public class Comment {
    String comment;
}
