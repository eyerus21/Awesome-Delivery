package miu.awesomedelivery.restaurantservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import miu.awesomedelivery.restaurantservice.dto.OrderMessage;
import miu.awesomedelivery.restaurantservice.dto.OrderRequest;
import miu.awesomedelivery.restaurantservice.model.Order;

public interface IOrderService {

    Order acceptOrder(String restId, String orderId) throws JsonProcessingException;
    Order rejectOrder(String restId, String orderId) throws JsonProcessingException;
    Order orderReady(String restId, String orderId) throws JsonProcessingException;

    Order createOrder(OrderMessage orderMessage);

}
