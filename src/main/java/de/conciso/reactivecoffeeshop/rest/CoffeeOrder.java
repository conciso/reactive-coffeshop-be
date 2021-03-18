package de.conciso.reactivecoffeeshop.rest;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonDeserialize
public class CoffeeOrder {
    String coffeeType;
    String customerName;
}
