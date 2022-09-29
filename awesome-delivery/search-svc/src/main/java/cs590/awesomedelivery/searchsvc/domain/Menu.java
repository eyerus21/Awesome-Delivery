package cs590.awesomedelivery.searchsvc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu implements Serializable {
    private String id;
    private String name;
    private String description;
    private Category category;
    private Double price;
}
