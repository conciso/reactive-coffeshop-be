package de.conciso.reactivecoffeeshop.model;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
public class Coffee {
    String coffeeType;
    String customerName;
    CoffeeState state;
}
