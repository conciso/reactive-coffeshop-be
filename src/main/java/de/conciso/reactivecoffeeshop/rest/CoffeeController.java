package de.conciso.reactivecoffeeshop.rest;

import de.conciso.reactivecoffeeshop.infra.CoffeeRepository;
import de.conciso.reactivecoffeeshop.model.Coffee;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@RestController
@AllArgsConstructor
public class CoffeeController {

    private final CoffeeRepository coffeeRepository;

    private final Sinks.Many<Coffee> coffeeSink;

    @PostMapping(path = "/order",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Coffee> orderCoffee(@RequestBody CoffeeOrder coffeeOrder) {
        return coffeeRepository.save(Coffee.fromOrder(coffeeOrder)).doOnNext(coffeeSink::tryEmitNext);
    }

}
