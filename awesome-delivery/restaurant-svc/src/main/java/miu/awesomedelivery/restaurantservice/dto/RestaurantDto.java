package miu.awesomedelivery.restaurantservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.awesomedelivery.restaurantservice.model.Address;
import miu.awesomedelivery.restaurantservice.model.Restaurant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {

    private String id;
    @JsonIgnore
    private String name;
    @JsonIgnore
    private Address address;

    public RestaurantDto create(Restaurant restaurant){
        RestaurantDto restaurantDto = new RestaurantDto();

        restaurantDto.setId(restaurant.getId());
        restaurantDto.setName(restaurant.getName());
        restaurantDto.setAddress(restaurant.getAddress());

        return restaurantDto;
    }
}
