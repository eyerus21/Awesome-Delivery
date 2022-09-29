package edu.miu.delivery.paymentsvc.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Payment {
    private String number;
    private String cvv;
    private int expireMonth;
    private int expireYear;
}
