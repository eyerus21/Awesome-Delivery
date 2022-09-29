package cs590.awesomedelivery.searchsvc.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs590.awesomedelivery.searchsvc.domain.Menu;
import cs590.awesomedelivery.searchsvc.domain.Restaurant;
import cs590.awesomedelivery.searchsvc.service.SearchService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger log = LoggerFactory.getLogger(SearchController.class);


    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getById(@PathVariable String id){
        return new ResponseEntity<>(searchService.findById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> search(
            @RequestParam(value="restaurant", required = false) String restaurantName,
            @RequestParam(value="menu", required = false) String menuName){

        if((restaurantName != null && !restaurantName.isBlank()) &&
                (menuName == null || menuName.isBlank())) {
            return new ResponseEntity<>(searchService.findByRestaurantName(restaurantName), HttpStatus.OK);
        }
        else if((restaurantName == null || restaurantName.isBlank()) &&
                (menuName != null && !menuName.isBlank())) {
            return new ResponseEntity<>(searchService.findByMenusName(menuName), HttpStatus.OK);
        }
        else if((restaurantName != null && !restaurantName.isBlank()) &&
                (menuName != null && !menuName.isBlank())) {
            return new ResponseEntity<>(searchService.findByNameAndMenusName(restaurantName, menuName), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(searchService.findAll(), HttpStatus.OK);
        }
    }

    @KafkaListener(topics ="#{'${kafka.topic.delete}'}", groupId = "#{'${spring.kafka.consumer.group-id}'}")
    public void consumeDelete(String id){
        Restaurant restaurant = searchService.findById(id);
        if(restaurant != null ) {
            searchService.deleteById(id);
            invalidateCache(restaurant);
            log.info("Restaurant [" + id + "] is deleted");
        }
    }

    @KafkaListener(topics ="#{'${kafka.topic.save}'}", groupId = "#{'${spring.kafka.consumer.group-id}'}")
    public void consumeSave(ConsumerRecord<String, String> cr, @Payload String payload){
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurant restaurant = new Restaurant();
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            restaurant = objectMapper.readValue(payload, Restaurant.class);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        searchService.save(restaurant);
        invalidateCache(restaurant);
        log.info("Restaurant ["+restaurant.getId()+"] is saved");
    }

    public void invalidateCache(Restaurant restaurant) {
        searchService.removeCacheForGetAll();
        searchService.removeCacheById(restaurant.getId());
        searchService.removeCacheByRestaurantName(restaurant.getName());
        List<Menu> menus =  restaurant.getMenu();
        if(menus != null)
        {
            for (Menu menu : menus)
            {
                searchService.removeCacheByNameAndMenuName(restaurant.getName(), menu.getName());
                searchService.removeCacheByMenuName(menu.getName());
            }
        }
    }
}
