package miu.awesomedelivery.restaurantservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import miu.awesomedelivery.restaurantservice.emuns.OrderStatus;
import miu.awesomedelivery.restaurantservice.model.Order;
import miu.awesomedelivery.restaurantservice.model.Restaurant;

import java.time.LocalDate;
import java.util.Collection;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderMessage {

//    @Autowired
//    private static RestaurantRepository repository;

    private String orderId;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate orderDate;
    private Customer customer;
    private RestaurantDto restaurant;
    private Collection<OrderMenu> menus;
    private Double totalPrice;
    private OrderStatus status;

    public static OrderMessage create(Order order, Restaurant restaurant){
        OrderMessage orderMessage = new OrderMessage();
        RestaurantDto restaurantDto = new RestaurantDto();

        orderMessage.setOrderId(order.getId());
        orderMessage.setOrderDate(order.getOrderDate());
        orderMessage.setMenus(order.getOrderMenu());
        orderMessage.setCustomer(order.getCustomer());

        orderMessage.setRestaurant(restaurantDto.create(restaurant));
        orderMessage.setMenus(order.getOrderMenu());
        orderMessage.setTotalPrice(order.getPrice());
        orderMessage.setStatus(order.getStatus());

        return orderMessage;
    }
}
