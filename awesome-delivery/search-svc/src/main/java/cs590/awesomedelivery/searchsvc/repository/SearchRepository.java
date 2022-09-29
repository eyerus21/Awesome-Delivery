package cs590.awesomedelivery.searchsvc.repository;

import cs590.awesomedelivery.searchsvc.domain.Restaurant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SearchRepository extends ElasticsearchRepository<Restaurant, String> {

    List<Restaurant> findByNameContaining(String name);
    List<Restaurant> findByMenuNameContaining(String menusName);
    Restaurant findByNameAndMenuName(String restaurantName, String menuName);
}