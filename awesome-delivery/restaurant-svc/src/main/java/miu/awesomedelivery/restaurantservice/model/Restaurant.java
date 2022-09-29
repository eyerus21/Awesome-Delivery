package miu.awesomedelivery.restaurantservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document
public class Restaurant {

    @Id
    private String id;
//    @JsonIgnore
    private String name;
//    @JsonIgnore
    private String userType;
//    @JsonIgnore
    @Indexed(unique = true)
    private String phoneNumber;
//    @JsonIgnore
    @Indexed(unique = true)
    private String email;
//    @JsonIgnore
    private Address address;
//    @JsonIgnore
    private List<Menu> menu;

}
