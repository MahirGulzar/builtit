package com.example.demo.procurement.domain.model.embedable;

import lombok.*;

import javax.persistence.Embeddable;

@Data
@Embeddable
@Value
@NoArgsConstructor(force = true,access = AccessLevel.PUBLIC)
public class Comment {
    String comment;

    public Comment(String comment)
    {
        this.comment=comment;
    }
}
