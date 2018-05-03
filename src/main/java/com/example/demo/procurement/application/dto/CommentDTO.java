package com.example.demo.procurement.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embeddable;

@Data
@AllArgsConstructor(staticName = "of")
public class CommentDTO {
    String comment;
}