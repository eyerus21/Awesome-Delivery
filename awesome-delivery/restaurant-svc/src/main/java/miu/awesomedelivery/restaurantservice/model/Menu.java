package miu.awesomedelivery.restaurantservice.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Menu {

    public enum Category{
        FOOD,DRINK
    }

    @Id
    private String id = new ObjectId().toString();
    private String name;
    private String description;
    private Category category;
    private double price;
    private String imageUrl;
}