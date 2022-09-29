package edu.miu.delivery.ordersvc.dto;

import edu.miu.delivery.ordersvc.domain.Order;
import edu.miu.delivery.ordersvc.domain.Payment;
import lombok.Data;

@Data
public class PaymentRequest {
    private String orderId;
    private String customerId;
    private Payment payment;
    private double price;

    public static PaymentRequest create(Order order) {
        PaymentRequest body = new PaymentRequest();
        body.setOrderId(order.getId());
        body.setPrice(order.totalPrice());
        body.setPayment(order.getCustomer().getPayment());
        body.setCustomerId(order.getCustomerId());
        return body;
    }
}
