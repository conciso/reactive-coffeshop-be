package de.conciso.reactivecoffeeshop.rest;

import de.conciso.reactivecoffeeshop.infra.CoffeeRepository;
import de.conciso.reactivecoffeeshop.model.Coffee;
import de.conciso.reactivecoffeeshop.model.CoffeeState;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@RestController
@AllArgsConstructor
public class CoffeeController {

    private final CoffeeRepository coffeeRepository;

    private final Sinks.Many<Coffee> coffeeSink;

    @PostMapping(path = "/api/coffee",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Coffee> orderCoffee(@RequestBody CoffeeOrder coffeeOrder) {
        return coffeeRepository.save(Coffee.fromOrder(coffeeOrder))
                .doOnNext(coffeeSink::tryEmitNext);
    }

    @PutMapping(path = "/api/coffee/brewing/{coffeeType}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Coffee> switchToBrewing(@PathVariable("coffeeType") String coffeeType) {
        return coffeeRepository.findFirstByCoffeeTypeAndState(coffeeType, CoffeeState.ORDERED)
                .map(coffee -> coffee.toBuilder()
                        .state(CoffeeState.PROCESSING)
                        .build())
                .flatMap(coffeeRepository::save)
                .doOnNext(coffeeSink::tryEmitNext);
    }
}
