package de.conciso.reactivecoffeeshop.core;

import de.conciso.reactivecoffeeshop.model.Coffee;
import de.conciso.reactivecoffeeshop.model.CoffeeState;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import reactor.core.publisher.Sinks;

@AllArgsConstructor
public class CoffeeChangeProducer {

    Sinks.Many<Coffee> coffeeSink;

    @Scheduled(fixedDelay = 5000)
    public void addCoffee() {
        coffeeSink.tryEmitNext(Coffee.builder()
                .coffeeType("Cappuccino")
                .customerName("Lars")
                .state(CoffeeState.ORDERED)
                .build());
    }

}
