package miu.awesomedelivery.restaurantservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import miu.awesomedelivery.restaurantservice.model.Order;
import miu.awesomedelivery.restaurantservice.model.Restaurant;
import miu.awesomedelivery.restaurantservice.repository.OrderRepository;
import miu.awesomedelivery.restaurantservice.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements IRestaurantService {

    private RestaurantRepository restaurantRepository;
    private OrderRepository orderRepository;
    KafkaTemplate<String, Object > kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Value("${app.restaurant-save-topic}")
    private String saveTopic;

    @Value("${app.restaurant-delete-topic}")
    private String deleteTopic;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository,
                                 KafkaTemplate<String, Object > kafkaTemplate,
                                 OrderRepository orderRepository) {
        this.restaurantRepository = restaurantRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.orderRepository = orderRepository;
    }

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) throws JsonProcessingException {
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        String vv = objectMapper.writeValueAsString(savedRestaurant);
        kafkaTemplate.send(saveTopic, restaurant.getId(), objectMapper.writeValueAsString(savedRestaurant));
        logger.info("Published message![key={} Payload={}]", restaurant.getId(), restaurant);

        return savedRestaurant;
    }

    @Override
    public Optional<Restaurant> getRestaurantById(String id) {
        return restaurantRepository.findById(id);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant updateRestaurant(String restId,Restaurant restaurant) throws JsonProcessingException {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restId);
        if (optionalRestaurant.isPresent()) {
            Restaurant savedRestaurant = restaurantRepository.save(restaurant);

            kafkaTemplate.send(saveTopic, restaurant.getId(), objectMapper.writeValueAsString(savedRestaurant));
            logger.info("Published message![key={} Payload={}]", restaurant.getId(), restaurant);

            return savedRestaurant;
        }
        return null;
    }

    @Override
    public String deleteRestaurant(String id) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        if (optionalRestaurant.isPresent()) {
            restaurantRepository.deleteById(id);
            kafkaTemplate.send(deleteTopic, id, id);
            logger.info("Published message![key={} Payload={}]", id, id);
            return "restaurant successfully deleted";
        } else {
            return "no restaurant found with id: " + id;
        }
    }

    @Override
    public List<Order> getOrders(String restId, String status){
        List<Order> orders = orderRepository.findByRestaurantId(restId);

        if(orders != null && status == "" || status ==  null){
            return orders;
        }
        else if(orders != null && status != ""){
            return orderRepository.findByRestaurantIdAndStatus(restId,status);
        }
        return null;
    }
}
