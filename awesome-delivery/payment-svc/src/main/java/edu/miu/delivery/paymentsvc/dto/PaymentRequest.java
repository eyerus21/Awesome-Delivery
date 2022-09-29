package edu.miu.delivery.paymentsvc.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PaymentRequest {
    private String orderId;
    private String customerId;
    private Payment payment;
    private double price;
}
