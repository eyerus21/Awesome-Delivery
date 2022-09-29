package miu.awesomedelivery.restaurantservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.awesomedelivery.restaurantservice.dto.Customer;
import miu.awesomedelivery.restaurantservice.dto.OrderMenu;
import miu.awesomedelivery.restaurantservice.emuns.OrderStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Order {

    @Id
    private String id;
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate orderDate;
    private OrderStatus status;
    private Customer customer;
    private String restaurantId;
    private Collection<OrderMenu> orderMenu;
    private double price;
}
