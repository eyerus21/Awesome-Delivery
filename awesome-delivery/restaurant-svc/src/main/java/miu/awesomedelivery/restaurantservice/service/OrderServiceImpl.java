package miu.awesomedelivery.restaurantservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import miu.awesomedelivery.restaurantservice.dto.OrderMessage;
import miu.awesomedelivery.restaurantservice.dto.OrderRequest;
import miu.awesomedelivery.restaurantservice.dto.RestaurantDto;
import miu.awesomedelivery.restaurantservice.emuns.OrderStatus;
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

import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService {

    private RestaurantRepository restaurantRepository;
    private OrderRepository orderRepository;
    private KafkaTemplate<String, Object> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private ObjectMapper objectMapper;


    @Value("${app.order-topic}")
    private String topic;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Autowired
    public OrderServiceImpl(RestaurantRepository restaurantRepository,
                            OrderRepository orderRepository,
                            KafkaTemplate<String, Object> kafkaTemplate) {
        this.restaurantRepository = restaurantRepository;
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Order createOrder(OrderMessage orderMessage) {

        Order order = new Order();
        order.setId(orderMessage.getOrderId());
        order.setOrderDate(orderMessage.getOrderDate());
        order.setCustomer(orderMessage.getCustomer());
        order.setRestaurantId(orderMessage.getRestaurant().getId());
        order.setOrderMenu(orderMessage.getMenus());
        order.setStatus(orderMessage.getStatus());

        return orderRepository.save(order);
    }

    @Override
    public Order acceptOrder(String restId, String orderId) throws JsonProcessingException {
        Optional<Order> order = orderRepository.findById(orderId);
        Optional<Restaurant> restaurant = restaurantRepository.findById(restId);
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.create(restaurant.get());

        if (order != null && order.get().getStatus() == OrderStatus.PAYED && restaurant != null) {
            order.get().setStatus(OrderStatus.ACCEPTED);

            Order order1 = order.get();
            System.out.println("Order accepted"+order1);
            kafkaTemplate.send(topic, order1.getId(),  objectMapper.writeValueAsString(OrderMessage.create(order1, restaurant.get())));
            logger.info("publishing message![key={} Payload={}]", order1.getId(), OrderMessage.create(order1, restaurant.get()));

            return orderRepository.save(order1);
        }
        return null;
    }


    @Override
    public Order rejectOrder(String restId, String orderId) throws JsonProcessingException {
        Optional<Order> order = orderRepository.findById(orderId);
        Optional<Restaurant> restaurant = restaurantRepository.findById(restId);
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.create(restaurant.get());

        if (order != null && order.get().getStatus() == OrderStatus.PAYED && restaurant != null) {
            order.get().setStatus(OrderStatus.REJECTED);


            kafkaTemplate.send(topic, order.get().getId(), objectMapper.writeValueAsString(OrderMessage.create(order.get(),restaurant.get())));
            logger.info("Received message![key={} Payload={}]", order.get().getId(), OrderMessage.create(order.get(),restaurant.get()));
            return orderRepository.save(order.get());
        }
        return null;
    }

    @Override
    public Order orderReady(String restId, String orderId) throws JsonProcessingException {
        Optional<Order> order = orderRepository.findById(orderId);
        Optional<Restaurant> restaurant = restaurantRepository.findById(restId);
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.create(restaurant.get());

        if (order != null && order.get().getStatus() == OrderStatus.ACCEPTED && restaurant != null) {
            order.get().setStatus(OrderStatus.READY);

            kafkaTemplate.send(topic, order.get().getId(), objectMapper.writeValueAsString(OrderMessage.create(order.get(), restaurant.get())));
            logger.info("Published message![key={} Payload={}]", order.get().getId(), OrderMessage.create(order.get(),restaurant.get()));

            return orderRepository.save(order.get());
        }
        return null;
    }
}
