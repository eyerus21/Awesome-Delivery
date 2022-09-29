package cs590.awesomedelivery.searchsvc.service;

import cs590.awesomedelivery.searchsvc.domain.Menu;
import cs590.awesomedelivery.searchsvc.domain.Restaurant;

import java.util.List;

public interface SearchService {
    Restaurant save(Restaurant restaurant);
    Restaurant findById(String id);
    List<Restaurant> findAll();
    List<Restaurant> findByRestaurantName(String name);
    List<Restaurant> findByMenusName(String name);
    List<Menu> findByNameAndMenusName(String restaurantName, String menuName);
    void deleteById(String id);
    void removeCacheForGetAll();
    void removeCacheById(String id);
    void removeCacheByRestaurantName(String name);
    void removeCacheByMenuName(String name);
    void removeCacheByNameAndMenuName(String restaurantName, String menuName);
}
