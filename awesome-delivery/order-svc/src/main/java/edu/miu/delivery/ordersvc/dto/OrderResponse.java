package edu.miu.delivery.ordersvc.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import edu.miu.delivery.ordersvc.domain.Menu;
import edu.miu.delivery.ordersvc.domain.Order;
import edu.miu.delivery.ordersvc.domain.Order.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderResponse {
    private String id;

    private String orderDate;
    private String customerId;
    private String restaurantId;
    private Collection<Menu> menus;
    private OrderStatus status;
    private Double totalPrice;

    public static OrderResponse create(Order order) {
        OrderResponse res = new OrderResponse();
        res.setId(order.getId());
        res.setOrderDate(order.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        res.setCustomerId(order.getCustomerId());
        res.setRestaurantId(order.getRestaurantId());
        res.setMenus(order.getMenus());
        res.setStatus(order.getStatus());
        res.setTotalPrice(order.totalPrice());
        return res;
    }
}
