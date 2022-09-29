package miu.awesomedelivery.restaurantservice.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import miu.awesomedelivery.restaurantservice.model.Order;
import miu.awesomedelivery.restaurantservice.model.Restaurant;
import miu.awesomedelivery.restaurantservice.service.RestaurantServiceImpl;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {


    private RestaurantServiceImpl restaurantService;
    private static final String orderTopic = "orderTopic";

    @Value("${app.restaurant-topic}")
    String restTopic;

    private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public RestaurantController(RestaurantServiceImpl restaurantService) {
        this.restaurantService = restaurantService;
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Restaurant>> getAllRestaurants(){
            return ResponseEntity.ok(restaurantService.getAllRestaurant());
    }
    /***
     * Restaurant user listener
     * **/

    @KafkaListener(topics = "topic-user",groupId = "restaurant-consumer")
    public void listenRestaurant(ConsumerRecord<String, String> cr, @Payload String payload) {
        logger.info("Restaurant Listener payload" + payload);
        try {
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            Restaurant restaurant = objectMapper.readValue(payload, Restaurant.class);
            System.out.println("======================"+restaurant);
            if (!"RESTAURANT".equals(restaurant.getUserType())) {
                return;
            }
            logger.info("Received message![key={} Payload={}]", cr.key(), restaurant);
            restaurantService.createRestaurant(restaurant);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    @PostMapping("/create")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) throws JsonProcessingException {
        return ResponseEntity.ok(restaurantService.createRestaurant(restaurant));
    }

    @PutMapping("/{restId}/update")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable String restId,@RequestBody Restaurant restaurant) throws JsonProcessingException {
            return ResponseEntity.ok(restaurantService.updateRestaurant(restId,restaurant));
    }

    @DeleteMapping("/{id}/delete")
    public String deleteRestaurant(@PathVariable String id){
       return restaurantService.deleteRestaurant(id);
    }

    @GetMapping("/{restId}/orders")
    public ResponseEntity<List<Order>> getOrdersByRestaurantID(@PathVariable String restId, @RequestParam(value = "status", required = false) String status){
        return ResponseEntity.ok(restaurantService.getOrders(restId,status));
    }
//
//'{
//        "orderId":"39e70619-458a-4870-9cef-6f528eb60647",
//        "orderDate":[2021,10,20],
//
//        "customer":{"id":"id123","address":{"street":"1000N","city":"Fairfield","state":"Iowa","country":"USA"}},
//            "restaurant":{"id":"restID123"},"orderMenus":[{"name":"Pizza","price":123.0,"quantity":2}],
//            "totalPrice":0.0,
//            "status":"ACCEPTED"}'
}
