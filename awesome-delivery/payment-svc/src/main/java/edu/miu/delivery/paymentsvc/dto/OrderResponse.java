package edu.miu.delivery.paymentsvc.dto;


import edu.miu.delivery.paymentsvc.domain.Menu;
import edu.miu.delivery.paymentsvc.domain.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Data
@NoArgsConstructor
public class OrderResponse {
    private String id;

    private String orderDate;
    private String customerId;
    private String restaurantId;
    private Collection<Menu> menus;
    private Order.OrderStatus status;
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
