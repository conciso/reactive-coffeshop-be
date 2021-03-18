package de.conciso.reactivecoffeeshop.websocket;

import de.conciso.reactivecoffeeshop.model.Coffee;
import de.conciso.reactivecoffeeshop.model.CoffeeState;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CoffeeMessage {

    String coffeeType;

    String customerName;

    CoffeeState state;

    public static CoffeeMessage from(Coffee coffee) {
        return CoffeeMessage.builder()
                .coffeeType(coffee.getCoffeeType())
                .customerName(coffee.getCustomerName())
                .state(coffee.getState())
                .build();
    }

}
