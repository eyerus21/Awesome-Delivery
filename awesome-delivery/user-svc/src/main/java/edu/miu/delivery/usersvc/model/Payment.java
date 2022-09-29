package edu.miu.delivery.usersvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
public class Payment {
        private String number;
        private String cvv;
        private int expireMonth;
        private int expireYear;
}

