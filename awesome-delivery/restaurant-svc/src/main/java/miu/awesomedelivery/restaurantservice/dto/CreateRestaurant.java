package miu.awesomedelivery.restaurantservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import miu.awesomedelivery.restaurantservice.model.Address;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateRestaurant {
    private String name;
    private String phoneNumber;
    private String email;
    private Address address;
}
