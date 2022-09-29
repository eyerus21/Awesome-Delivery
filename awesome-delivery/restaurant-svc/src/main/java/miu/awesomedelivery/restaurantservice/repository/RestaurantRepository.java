package miu.awesomedelivery.restaurantservice.repository;

import miu.awesomedelivery.restaurantservice.dto.CreateRestaurant;
import miu.awesomedelivery.restaurantservice.model.Menu;
import miu.awesomedelivery.restaurantservice.model.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant,String> {



}
