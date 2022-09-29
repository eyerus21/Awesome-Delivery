package miu.awesomedelivery.restaurantservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import miu.awesomedelivery.restaurantservice.model.Menu;
import miu.awesomedelivery.restaurantservice.model.Restaurant;
import miu.awesomedelivery.restaurantservice.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    RestaurantRepository repository;
    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Value("${app.restaurant-save-topic}")
    private String saveTopic;

    public MenuService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public List<Menu> getAllMenusOfRestaurant(String id) {
        Optional<Restaurant> restaurant = repository.findById(id);
        if (restaurant.isPresent()) {
            return restaurant.get().getMenu();
        } else return null;
    }

    public Menu addMenu(String restId, Menu menu) throws JsonProcessingException {
        Optional<Restaurant> restaurant = repository.findById(restId);

        if (restaurant.isPresent()){
            //restaurant.get().getMenu().add(menu);

            List<Menu> menus =  restaurant.get().getMenu();
            if(menus == null)
            {
                menus = new ArrayList<>();
            }
            menus.add(menu);
            restaurant.get().setMenu(menus);

            Restaurant savedRestaurant = repository.save(restaurant.get());
            kafkaTemplate.send(saveTopic, restaurant.get().getId(), objectMapper.writeValueAsString(savedRestaurant));
            logger.info("Published message![key={} Payload={}]", restaurant.get().getId(), restaurant);
            return menu;
        }
        return null;


    }

    public Menu updateMenu(String restId, String menuId, Menu menu) throws Exception {
        Restaurant restaurant = repository.findById(restId).orElseThrow(() -> new Exception("Restaurant not found"));
        List<Menu> menus = restaurant.getMenu();

        for (Menu menu1 : menus) {
            if (menu1.getId().equals(menuId)) {
                menus.remove(menu1);
                menus.add(menu);
                restaurant.setMenu(menus);

                Restaurant savedRestaurant = repository.save(restaurant);
                kafkaTemplate.send(saveTopic, restaurant.getId(), objectMapper.writeValueAsString(savedRestaurant));
                logger.info("Published message![key={} Payload={}]", restaurant.getId(), restaurant);
                return menu;
            }
        }
        throw new Exception("Menu not found");
    }

    public String deleteMenu(String restId, String menuId) throws Exception {
        Restaurant restaurant = repository.findById(restId).orElseThrow(() -> new Exception("Restaurnat not found"));
        List<Menu> menus = restaurant.getMenu();


        for (Menu menu1 : menus) {
            if (menu1.getId().equals(menuId)) {
                menus.remove(menu1);
                restaurant.setMenu(menus);

                Restaurant savedRestaurant = repository.save(restaurant);
                kafkaTemplate.send(saveTopic, restaurant.getId(), objectMapper.writeValueAsString(savedRestaurant));
                logger.info("Published message![key={} Payload={}]", restaurant.getId(), restaurant);
                return "menu with id: " + menuId + " deleted successfully";
            }
        }
        return "menu with id: " + menuId + "  not found";
    }
}
