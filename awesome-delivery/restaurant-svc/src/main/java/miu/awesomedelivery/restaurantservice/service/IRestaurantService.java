package miu.awesomedelivery.restaurantservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import miu.awesomedelivery.restaurantservice.dto.OrderMessage;
import miu.awesomedelivery.restaurantservice.model.Menu;
import miu.awesomedelivery.restaurantservice.model.Order;
import miu.awesomedelivery.restaurantservice.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface IRestaurantService {
    Restaurant createRestaurant(Restaurant restaurant) throws JsonProcessingException;
    Optional<Restaurant> getRestaurantById(String id);
    Restaurant updateRestaurant(String restId,Restaurant restaurant) throws JsonProcessingException;
    List<Restaurant> getAllRestaurant();
    String deleteRestaurant(String id);

    List<Order> getOrders(String restId,String status);
}
