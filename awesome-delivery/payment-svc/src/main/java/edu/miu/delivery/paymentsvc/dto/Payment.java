package edu.miu.delivery.paymentsvc.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Payment {
    private String number;
    private String cvv;
    private int expireMonth;
    private int expireYear;
}
