package edu.miu.delivery.usersvc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.miu.delivery.usersvc.model.Restaurant;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, String> {
}
