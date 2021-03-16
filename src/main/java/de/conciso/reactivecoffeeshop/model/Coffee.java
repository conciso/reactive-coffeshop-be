package de.conciso.reactivecoffeeshop.model;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
@Builder
@ToString
public class Coffee {
    @Id
    Long id;
    String coffeeType;
    String customerName;
    CoffeeState state;
}
