package de.conciso.reactivecoffeeshop.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class CoffeeController {

    @PostMapping(path = "/api/coffee",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> orderCoffee(@RequestBody CoffeeOrder coffeeOrder) {
        return Mono.empty();
    }

    @PutMapping(path = "/api/coffee/processing/{coffeeType}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> switchToProcessing(@PathVariable("coffeeType") String coffeeType) {
        return Mono.empty();
    }

    @PutMapping(path = "/api/coffee/brewed/{coffeeType}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> switchToBrewed(@PathVariable("coffeeType") String coffeeType) {
        return Mono.empty();
    }
}
