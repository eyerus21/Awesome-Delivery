package miu.awesomedelivery.restaurantservice.repository;

import miu.awesomedelivery.restaurantservice.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order,String> {

    List<Order> findByRestaurantId(String id);
    List<Order> findByRestaurantIdAndStatus(String id,String status);
}
