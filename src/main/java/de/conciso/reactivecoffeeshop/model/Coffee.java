package de.conciso.reactivecoffeeshop.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
@Builder(toBuilder = true)
public class Coffee {

    @Id
    Long id;

    String coffeeType;

    String customerName;

    CoffeeState state;
}
