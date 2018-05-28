package com.example.demo.procurement.domain.model;

import com.example.demo.procurement.domain.model.enums.POStatus;
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
    POStatus poStatus;

    public String getExtentionURL() {
        String splitted[] = this.href.split("/");
        System.out.println(splitted[2]);
        String url = splitted[2];
        switch (url) {
            case "team-2-rentit.herokuapp.com":
                return this.href + "/extensions";
            default:
                return this.href + "/extensions";
        }
    }

}
