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
public class Customer {

    private String id;
    private Address address;
}
