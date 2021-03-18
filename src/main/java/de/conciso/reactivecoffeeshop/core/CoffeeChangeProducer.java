package de.conciso.reactivecoffeeshop.core;

import de.conciso.reactivecoffeeshop.infra.CoffeeRepository;
import de.conciso.reactivecoffeeshop.model.Coffee;
import de.conciso.reactivecoffeeshop.model.CoffeeState;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import reactor.core.publisher.Sinks;

@AllArgsConstructor
public class CoffeeChangeProducer {

    CoffeeRepository coffeeRepository;

    Sinks.Many<Coffee> coffeeSink;

    @Scheduled(fixedDelay = 5000)
    public void addCoffee() {
        coffeeRepository.save(Coffee.builder()
                .coffeeType("Cappuccino")
                .customerName("Lars")
                .state(CoffeeState.ORDERED)
                .build()).map(coffeeSink::tryEmitNext).block();
    }

}
