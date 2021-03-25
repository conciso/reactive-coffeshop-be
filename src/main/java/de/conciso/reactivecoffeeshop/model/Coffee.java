package de.conciso.reactivecoffeeshop.model;

import de.conciso.reactivecoffeeshop.rest.CoffeeOrder;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@Value
@Builder(toBuilder = true)
public class Coffee {

    @Id
    Long id;

    String coffeeType;

    String customerName;

    CoffeeState state;

    @Transient
    boolean update;

    public static Coffee fromOrder(CoffeeOrder coffeeOrder) {
        return Coffee.builder()
                .coffeeType(coffeeOrder.getCoffeeType())
                .customerName(coffeeOrder.getCustomerName())
                .state(CoffeeState.ORDERED)
                .build();
    }
}
